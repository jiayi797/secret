import pandas as pd

data = pd.read_csv('20160901_od.txt', sep = ',')
toflename = "20160901_path.csv"
print(data.dtypes)
data = data[['id','time','longitude','latitude','load']]
data['time'] = pd.to_datetime(data['time']) 
groups = data.groupby('id')
first = True
i = 0
for name, group in groups:
	if i % 100 == 0:
		print(i)
	i = i + 1
	#print(name, group[['time','load']])
	group[['load_d','longitude_d','latitude_d','time_d']] = group[['load','longitude','latitude','time']].shift(-1)
	group['gap'] = group['time_d'] - group['time']
	group = group[(group['load'] == 1)&(group['load_d'] == 0)&(group['gap'] >= pd.to_timedelta('10m')) &(group['gap'] <= pd.to_timedelta('200m'))]
	del group['time']
	del group['time_d']
	#print(name, group) # [['time','load','load_d']]
	if first:
		group.to_csv(toflename, index = False, mode = 'w')
		first = False
	else:
		group.to_csv(toflename, index = False, header = False, mode = 'a')
