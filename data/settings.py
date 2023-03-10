# 数据库连接
DATABASES = {
    'default': {
        "HOST": "localhost",  # 连接MySQL数据库的IP
        "PORT": 3306,  # 端口号 ，默认是3306
        'NAME': "bus",  # 需要我们在Mysql数据库中手动创建一个数据库
        "USER": "root",  # 登录MySQL数据库的用户名。
        "PASSWORD": "123456",  # 登录MySQL的密码。
    }
}
