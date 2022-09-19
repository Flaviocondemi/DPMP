import matplotlib.pyplot as plt
import numpy
import pymongo
import matplotlib.pyplot as plt
import numpy as np
from sklearn import datasets, linear_model
from sklearn.linear_model import LinearRegression
from sklearn.metrics import mean_squared_error, r2_score
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import PolynomialFeatures

'''
  ____                 _       ____  _       _   
 / ___|_ __ __ _ _ __ | |__   |  _ \| | ___ | |_ 
| |  _| '__/ _` | '_ \| '_ \  | |_) | |/ _ \| __|
| |_| | | | (_| | |_) | | | | |  __/| | (_) | |_ 
 \____|_|  \__,_| .__/|_| |_| |_|   |_|\___/ \__|
                |_|                              

'''

#MONGODB CONNECTION CONFIG
client = pymongo.MongoClient("mongodb://localhost:27017/")
ResponseTimeMetricsDB = client["ResponseTimeMetrics"]
metricsGetPeople = ResponseTimeMetricsDB["GetPeopleMetrics"]

metricsGetTrain = list(metricsGetPeople.find())
metricsGetTest = list(metricsGetPeople.find())

#RESPONSE TIME LIST Y-AXIS
train_response_time_list = [int(train_date['response-time']) for train_date in metricsGetTrain[0:8000]]
test_response_time_list = [int(test_date['response-time']) for test_date in metricsGetTest[8000:13000]]

#AMOUNT-PEOPLE TIME LIST X-AXIS
train_amount_people_list = [int(train_date['amount-people']) for train_date in metricsGetTrain[0:8000]]
test_amount_people_list = [int(test_date['amount-people']) for test_date in metricsGetTest[8000:13000]]


print("Tipo train_response_time_list: " + str(type(train_amount_people_list)))

#Sort train list of amount of people and response time
train_response_time_list.sort()
train_response_time_list_shaped = np.array(train_response_time_list).reshape(-1, 1)
train_amount_people_list.sort()
train_amount_people_list_shaped = np.array(train_amount_people_list).reshape(-1, 1)


#Sort test list of amount of people and response time
test_response_time_list.sort()
test_response_time_list_shaped = np.array(test_response_time_list).reshape(-1, 1)
test_amount_people_list.sort()
test_amount_people_list_shaped = np.array(test_amount_people_list).reshape(-1, 1)

print(test_amount_people_list)

'''
 _     _                       
| |   (_)_ __   ___  __ _ _ __ 
| |   | | '_ \ / _ \/ _` | '__|
| |___| | | | |  __/ (_| | |   
|_____|_|_| |_|\___|\__,_|_|   
                               
 ____                              _             
|  _ \ ___  __ _ _ __ ___  ___ ___(_) ___  _ __  
| |_) / _ \/ _` | '__/ _ \/ __/ __| |/ _ \| '_ \ 
|  _ <  __/ (_| | | |  __/\__ \__ \ | (_) | | | |
|_| \_\___|\__, |_|  \___||___/___/_|\___/|_| |_|
           |___/                                 

'''
#train_test_split(): Split arrays or matrices into random train and test subsets.
x_train, x_test, y_train, y_test = train_test_split(
    np.array(train_amount_people_list).reshape(-1, 1), np.array(train_response_time_list).reshape(-1, 1)
    , test_size=0.2, random_state=2)

lr = LinearRegression()
lr.fit(x_train, y_train)
y_pred = lr.predict(x_test)
print(r2_score(y_test, y_pred))

plt.plot(x_train, lr.predict(x_train), color="r")
plt.plot(train_amount_people_list, train_response_time_list, "b.")
plt.xlabel("Amount-people")
plt.ylabel("Response-time")
plt.show()

'''
 ____       _                             _       _ 
|  _ \ ___ | |_   _ _ __   ___  _ __ ___ (_) __ _| |
| |_) / _ \| | | | | '_ \ / _ \| '_ ` _ \| |/ _` | |
|  __/ (_) | | |_| | | | | (_) | | | | | | | (_| | |
|_|   \___/|_|\__, |_| |_|\___/|_| |_| |_|_|\__,_|_|
              |___/                                 
 ____                              _             
|  _ \ ___  __ _ _ __ ___  ___ ___(_) ___  _ __  
| |_) / _ \/ _` | '__/ _ \/ __/ __| |/ _ \| '_ \ 
|  _ <  __/ (_| | | |  __/\__ \__ \ | (_) | | | |
|_| \_\___|\__, |_|  \___||___/___/_|\___/|_| |_|
           |___/                                 
'''


#applying polynomial regression degree 2
poly = PolynomialFeatures(degree=1, include_bias=True)
x_train_trans = poly.fit_transform(x_train)
x_test_trans = poly.transform(x_test)
#include bias parameter
lr = LinearRegression()
lr.fit(x_train_trans, y_train)
y_pred = lr.predict(x_test_trans)
print("r2 score : " + str(r2_score(y_test, y_pred)))


print(lr.coef_)
print(lr.intercept_)

X_new = np.linspace(0, 2000, 2000).reshape(2000, 1)
X_new_poly = poly.transform(X_new)
y_new = lr.predict(X_new_poly)
print("Predetti: " + str(X_new))
plt.plot(X_new, y_new, "r-", linewidth=2, label="Predictions")
plt.plot(x_train, y_train, "b.", label='Training points')
plt.plot(x_test, y_test, "g.", label='Testing points')
plt.xlabel("Amount-people")
plt.ylabel("Response-time")
plt.legend()
plt.show()