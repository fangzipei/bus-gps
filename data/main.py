import pymysql
import pandas as pd
from .settings import DATABASES

if __name__ == "__main__":
    host = DATABASES["default"]["HOST"]
    user = DATABASES["default"]["USER"]
    passwd = DATABASES["default"]["PASSWORD"]
    port = DATABASES["default"]["PORT"]
    db = DATABASES["default"]["NAME"]
    connection = pymysql.connect(host=host, user=user, password=passwd, database=db, port=port)
    cursor = connection.cursor()

    data = pd.read_csv("wulumuqi_station.csv")
    stop_sql = "insert into stop_info(stop_name, longitude, latitude) values "
    line_sql = "insert into bus_stop(bus_no, order, stop_name, heading_type) values"
    bus_sql = "insert into bus_info(bus_no, up_start, up_end, down_start, down_end) values"
    update_bus = "update bus_info set down_start = '{}', down_end = '{}' where bus_no = '{}' "
    bus_map = {}
    stop_map = {}
    stop_sql_param = ""
    line_sql_param = ""
    for i in range(0, len(data["line_name"])):
        bus_all_name = data["line_name"][i]
        no_end = bus_all_name.find("(")
        bus_no = bus_all_name[:no_end]
        start_end_stop = bus_all_name[no_end + 1:-1]
        stop_arr = start_end_stop.split("--")
        heading_type = 1
        # bus_info表更新
        if bus_no in bus_map.keys() and (bus_map[bus_no][0] != stop_arr).all():
            heading_type = 2
            if len(bus_map) == 1:
                bus_map[bus_no].append([stop_arr])
                update_bus_temp = update_bus.copy()
                update_bus_temp.format(stop_arr[0], stop_arr[1], bus_no)
                try:
                    cursor.execute(update_bus_temp)
                    connection.commit()
                    print("公交：{}，下行站台：{},{}，添加成功".format(bus_no, stop_arr[0], stop_arr[1]))
                except Exception as e:
                    print(e)
        elif bus_no not in bus_map.keys():
            bus_map[bus_no] = [[stop_arr[0], stop_arr[1]]]
            bus_sql_temp = bus_sql.copy()
            bus_sql_temp += "({},{},{},{},{})".format(bus_no, stop_arr[0], stop_arr[1])
            try:
                cursor.execute(bus_sql_temp)
                connection.commit()
                print("公交：{}，上行站台：{},{}，添加成功".format(bus_no, stop_arr[0], stop_arr[1]))
            except Exception as e:
                connection.rollback()
                print(e)

        # stop_info及bus_stop更新
        stop_name = data['station_name'][i]
        order = data['sequence'][i]
        x = data['coord_x'][i]
        y = data['coord_y'][i]
        if line_sql_param == "":
            line_sql_param += " ('{}',{},'{}','{}')".format(bus_no, order, stop_name, heading_type)
        else:
            line_sql_param += ", ('{}',{},'{}','{}')".format(bus_no, order, stop_name, heading_type)
        if stop_name not in stop_map.keys():
            stop_map[stop_name] = [x,y]
            if stop_sql_param == "":
                stop_sql_param += "('{}','{}','{})".format(bus_no, x, y)
            else:
                stop_sql_param += ", ('{}','{}','{})".format(bus_no, x, y)
        if i % 100 == 0:
            try:
                cursor.execute(stop_sql + stop_sql_param)
                cursor.execute(line_sql + line_sql_param)
                connection.commit()
                stop_sql_param = ""
                line_sql_param = ""
            except Exception as e:
                connection.rollback()
                print(e)
    if line_sql_param != "":
        try:
            cursor.execute(line_sql + line_sql_param)
            connection.commit()
        except Exception as e:
            connection.rollback()
            print(e)
    if stop_sql_param != "":
        try:
            cursor.execute(stop_sql + stop_sql_param)
            connection.commit()
        except Exception as e:
            connection.rollback()
            print(e)
    cursor.close()
    connection.close()
