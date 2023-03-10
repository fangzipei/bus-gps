# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://docs.scrapy.org/en/latest/topics/item-pipeline.html


# useful for handling different item types with a single interface
import pymysql
import logging
from settings import DATABASES

log = logging.getLogger("bus")


class MySqlPipeline:
    def open_spider(self, spider):
        host = DATABASES["default"]["HOST"]
        user = DATABASES["default"]["USER"]
        passwd = DATABASES["default"]["PASSWORD"]
        port = DATABASES["default"]["PORT"]
        db = DATABASES["default"]["NAME"]
        self.connection = pymysql.connect(host=host, user=user, password=passwd, database=db, port=port)
        self.cursor = self.connection.cursor()
        self.items = []

    def process_item(self, item, spider):
        self.items.append(item)
        if len(self.items) == 100:
            sql = "update bus_info set run_time= case bus_no "
            temp = "when '{}' then '{}' "
            bus_arr = []
            for i, v in enumerate(self.items):
                now_temp = str(temp)
                now_temp = now_temp.format(
                    v['bus_no'],
                    v['run_time']
                )
                bus_arr.append(r" '{}'".format(v['bus_no']))
                sql += now_temp
            sql += " end where bus_no in ({})".format(",".join(bus_arr))
            try:
                log.info(sql)
                self.cursor.execute(sql)  # 执行sql语句，也可执行数据库命令，如：show tables
                self.connection.commit()
                log.info('更新成功')
            except Exception as e:
                self.connection.rollback()
                log.info('更新失败')
            finally:
                self.items = []
        return item

    def close_spider(self, spider):
        if len(self.items) > 0:
            sql = "update bus_info set run_time= case bus_no "
            temp = "when '{}' then '{}' "
            bus_arr = []
            for i, v in enumerate(self.items):
                now_temp = str(temp)
                now_temp = now_temp.format(
                    v['bus_no'],
                    v['run_time']
                )
                bus_arr.append(r" '{}'".format(v['bus_no']))
                sql += now_temp
            sql += " end where bus_no in ({})".format(",".join(bus_arr))
            try:
                log.info(sql)
                self.cursor.execute(sql)  # 执行sql语句，也可执行数据库命令，如：show tables
                self.connection.commit()
                log.info('更新成功')
            except Exception as e:
                self.connection.rollback()
                log.error(f'更新失败，错误：{e}')
                self.cursor.close()
                self.connection.close()
        self.cursor.close()
        self.connection.close()
