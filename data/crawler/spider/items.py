# Define here the models for your scraped items
#
# See documentation in:
# https://docs.scrapy.org/en/latest/topics/items.html

import scrapy


class BusInfoItem(scrapy.Item):
    # define the fields for your item here like:
    # name = scrapy.Field()
    bus_no = scrapy.Field()
    up_start = scrapy.Field()
    up_end = scrapy.Field()
    down_start = scrapy.Field()
    down_end = scrapy.Field()
    run_time = scrapy.Field()


