import numpy as np 
import pandas as pd 
import matplotlib.pyplot as plt 
from fbprophet import Prophet
import MySQLdb
from datetime import datetime
import time
import math
import csv
from pandas import DatetimeIndex
from sklearn.svm import SVR

dates = []
prices = []


connection = MySQLdb.connect ("localhost", "root", "nature101","citi")

cur = connection.cursor ()

sql1 = "select code from company"
cur.execute(sql1)
data1 = cur.fetchall()
data2 = list(data1)
print(data2)
print(len(data2))

for a1 in data2:
    b=a1;
    print(b)
    query = "select Date,Open,High,Low,Last,Close,Total_Trade_Quantity,Turnover from stocks where Code = %(Code)s"
    cur.execute(query,{ 'Code' : b})
    data = cur.fetchall()
    df = pd.DataFrame(list(data),columns=['Date','Open','High','Low','Last','Close','Total Trade Quantity','Turnover'])
    data3 = DatetimeIndex(df['Date']).day
    data3 = np.asarray(data3)
    dates = np.reshape(data3,(len(data3), 1))
    prices = np.asarray(df['Close'])
    svr_poly = SVR(kernel= 'poly', C= 1e3, degree= 2)
    svr_poly.fit(dates, prices)
    length = len(prices) + 1
    print(length)
    aaa = svr_poly.predict(length)[0]
    print("The next predicted price is:")
    print(aaa)
    query = "select Close from stocks where Code = %(Code)s"
    cur.execute(query, {'Code' : b})
    data = cur.fetchall()
    df1 = pd.DataFrame(list(data),columns = ['Close'])
    df1 = df1.tail(1)
    print(df1)
    k = aaa - df1
    print(k)
    j=k.head(1)
    j = pd.Series(j)
    i=float(j)
    query = "update company set risk3 = %(risk3)s where Code = %(Code)s"
    cur.execute(query, {'risk3': i , 'Code' : b})
    connection.commit()

connection.close()
