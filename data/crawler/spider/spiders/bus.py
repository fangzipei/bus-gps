import logging
import scrapy
import time
from scrapy import cmdline
from scrapy.http import Request
from crawler.spider.items import BusInfoItem

log = logging.getLogger("bus")


class BusSpider(scrapy.Spider):
    name = "bus"
    allowed_domains = ["wulumuqi.8684.cn"]
    start_urls = ["https://wulumuqi.8684.cn/line1"]

    def __init__(self, **kwargs):
        super().__init__(**kwargs)
        self.url_num = 0
        self.apartment_num = 0

    def parse(self, response, **kwargs):
        regions = response.css('div.category > a')
        for region in regions:
            url = 'https://wulumuqi.8684.cn' + region.attrib['href']
            yield Request(url, callback=self.parse_region)

    def parse_region(self, response):
        try:
            bus_a = response.css('div.list.clearfix > a')
            bus_nos = response.css('div.list.clearfix > a::text').extract()
            for i, bus in enumerate(bus_a):
                item = BusInfoItem()
                time.sleep(0.1)
                url = 'https://wulumuqi.8684.cn' + bus.attrib['href']
                item['bus_no'] = bus_nos[i].strip()
                yield Request(url, meta={'item': item}, callback=self.parse_overview)
        except Exception as e:
            log.error(e)
            log.error("解析分页信息出错，url：%s", response.url)

    def parse_overview(self, response):
        run_time = response.css('ul.bus-desc > li:nth-child(1)::text').get()
        run_time = run_time[run_time.find('：') + 1:]
        item = response.meta['item']
        item['run_time'] = run_time.strip()
        yield item  # 返回数据


if __name__ == "__main__":
    cmdline.execute('scrapy crawl bus'.split())
