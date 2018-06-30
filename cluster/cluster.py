from sklearn.cluster import KMeans,MiniBatchKMeans
import matplotlib.pyplot as plt      #python画图包  
# from k_means_plus_plus import *
from scipy.spatial import distance
import time
import pandas as pd
import numpy as np
def kmean2(data):
    # Cluster
    #data_in = np.array(data.values)
    km = KMeans(n_clusters=10000)
    km.fit(np.array(list(zip(data['latitude'], data['longitude']))))
    #km.fit(data.as_matrix())
    print ('train finshed')
    # Get cluster assignment labels
    labels = km.labels_
    plt_cluster(data['latitude'],data['longitude'],labels)
    return labels

def batchKmeans(x,y,k):
    batch_size = 100
    mbk = MiniBatchKMeans(init='k-means++', n_clusters=k, batch_size=batch_size,
                      n_init=10, max_no_improvement=10, verbose=0,init_size = None)
    t0 = time.time()
    mbk.fit(np.array(list(zip(x,y))))
    t_mini_batch = time.time() - t0
    print ('train finshed')
    # Get cluster assignment labels
    labels = mbk.labels_
    plt_cluster(x,y,labels)
    return labels

def plt_cluster(x,y,color):
    plt.subplot(111)  #在2图里添加子图1  
    plt.scatter(x,y, c=color) #scatter绘制散点  
    plt.title("Incorrect Number of Blobs")   #加标题 
    plt.show()
def draw_hist(data):  #data 接受的其实是 sizeArry传来的数组 就是def get_data(lines) 返回的数据
    
    #print data
   

    # 对数据进行切片，将数据按照从最小值到最大值分组，分成20组
   # bins = np.linspace(min(data),max(data),20)

    # 这个是调用画直方图的函数，意思是把数据按照从bins的分割来画
    plt.hist(data,100)
    plt.xlim((0,100))
    #设置出横坐标
    plt.xlabel('Number of car od')
    #设置纵坐标的标题
    plt.ylabel('Number of occurences')
    #设置整个图片的标题
    plt.title('Frequency distribution of number of car od')
    # 展示出我们的图片
    plt.show()

from sklearn.cluster import AffinityPropagation
def ap(longitude,latitude, f = 10):
    print('size = ', len(longitude))
    # compute simility matrix
    r = 6373.0
    x = r*np.cos(longitude)*np.sin(latitude)
    y = r*np.sin(longitude)*np.sin(latitude)
    z = r*np.cos(latitude)
    point = np.array([x,y,z]).T
    matrix = -distance.cdist(point,point)
    preference = []
    for i in range(matrix.shape[0]):
        preference.append(np.sum(matrix[i] * 10))#/(matrix.shape[0]-1))*f
    af = AffinityPropagation(preference=preference).fit(matrix)
    cluster_centers_indices = af.cluster_centers_indices_
    labels = af.labels_
    return labels
def import_data():
    file = "2016090"
    k = 5000
    nrows_in = -1

    i = 1
    datalist = []
    while( i <= 1):
        #data_i = pd.read_csv("od/20160901_od.txt")
        data_i = 0
        if nrows_in == -1:
            data_i = pd.read_csv(file + str(i) + "_od.txt")
        else :
            data_i = pd.read_csv(file + str(i) + "_od.txt", nrows = nrows_in)
        counter = data_i[['id']]
        counter['counter'] = 1
        counter = counter.groupby("id").agg("sum").reset_index()
        counter = counter[counter['counter'] >= 10] # 筛掉小于10个od点的     
        #print counter
        # draw_hist(counter['counter']) 直方图
        data_i = pd.merge(counter, data_i, how = 'left', on = 'id')
        
        datalist.append(data_i)
        i = i + 1
    
    data = pd.concat(datalist)
    data = data.sample(n = 15000)
    print('size', data.shape)
    data['longitude'] = data['longitude']/100000
    data['latitude'] = data['latitude']/100000
    data = data[(data['longitude'] >= 115) & (data['longitude'] <= 118) & (data['latitude'] >= 35) & (data['latitude'] <= 41)]
    data = data.reset_index()
    #print  data.head(10)
    print (data.dtypes)
    print ('cluster_begin')
    #data['center_idx'] = kmean2(data[['latitude','longitude']])    
    data['center_idx'] = ap(data['latitude'],data['longitude'])
    data.to_csv("kmeans_result.csv")
    
    center = data[['center_idx']].drop_duplicates()
    f = data[['index','counter','latitude','longitude']]
    center = pd.merge(center, f, left_on = 'center_idx', right_on = 'index', how = 'left')
    center = center[['counter','latitude','longitude']]
    center.to_csv("centers.csv", index = False)
    print ('center.shape',center.shape)
    
import_data()