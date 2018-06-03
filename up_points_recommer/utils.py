import requests

def convert_request(loc):
    api = "http://restapi.amap.com/v3/assistant/coordinate/convert?"
    api += ('key=' + '2a19f25ef9117a9c5b32c96b6f57dedd')
    api += ('&locations='+str(loc[1])+','+str(loc[0]))
    api += ('&coordsys=gps')
    return requests.get(api).json()
def convert(file, file_to):
    file = 'E:\\实验室\\公交数据\\毕设代码\\centers.csv'
    file_to = 'E:\\实验室\\公交数据\\毕设代码\\centers_gd.txt'
    file_to = open(file_to, 'w')
    center = open(file, 'r')
    center.readline()
    center = center.readlines()
    for i in range(len(center)):
        loc = center[i]
        loc = loc.split(',')
        loc = loc[1:3]
        loc[0] = int(loc[0])/100000
        loc[1] = int(loc[1])/100000
        report = convert_request(loc)
        report = report['locations']
        file_to.write(report+'\n')
        if i % 100 == 0:
            print (i, report)
    file_to.close()