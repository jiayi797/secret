import pandas as pd
import numpy as np

filename = "20160901_candidates.txt"
candidates = []
name = ['orderId','longitude','latitude','walkingDistance','walkingTime','carDistance','carTime','id']
#candidates.append(name)
f = open(filename,'r')  
i = 0
for line in f:  
    line = f.readline()  
    line = line.split(";")
    for j in range(len(line)):
    	a_candidate = line[j].split(',')
    	if len(a_candidate) != 6:
    		continue
    	a_candidate = [(float)(e) for e in a_candidate]
    	a_candidate = [i] + a_candidate
    	a_candidate = a_candidate + [j]
    	candidates.append(a_candidate)
    i = i + 1
candidates = pd.DataFrame(candidates, columns = name)
f.close()                  
print(candidates.head(5))

# 统计最好的点的时间、距离
best = candidates[candidates['id'] == 0]
#print(best.head(5))
best_walkingDistance = best['walkingDistance'].mean()
best_walkingTime = best['walkingTime'].mean()
best_carDistance = best['carDistance'].mean()
best_carTime = best['carTime'].mean()
print(best_walkingDistance, best_walkingTime, best_carDistance, best_carTime)

# 随机选一个点，统计时间、距离
replace = True  # with replacement
fn = lambda obj: obj.loc[np.random.choice(obj.index, 1, replace),:]
random = candidates.groupby('orderId', as_index=False).apply(fn)

random_walkingDistance = random['walkingDistance'].mean()
random_walkingTime = random['walkingTime'].mean()
random_carDistance = random['carDistance'].mean()
random_carTime = random['carTime'].mean()

print(random_walkingDistance, random_walkingTime, random_carDistance, random_carTime)


print(best.shape, random.shape)
import matplotlib.pyplot as plt
x = np.linspace(0,best.shape[0], best.shape[0])

result = pd.merge(best, random, on = ['orderId'], how = 'left')

print(result.head(10))

name = 'carTime'
result = result.sort_values(by = [name+"_x"])
plt.plot(x, result[name+"_x"], 'r*-', label = 'best '+name)
plt.plot(x, result[name+"_y"], 'b*-', label = 'random '+name)
plt.title(name + " of best and random up_point / s")
#plt.plot(x, result[name+"_y"] - result[name+"_x"], 'g', label = 'lag')
plt.legend()
plt.show()