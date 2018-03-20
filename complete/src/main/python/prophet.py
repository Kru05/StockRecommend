import numpy as np 
import pandas as pd 
import matplotlib.pyplot as plt 
from fbprophet import Prophet
import MySQLdb
from datetime import datetime
import time
import math



connection = MySQLdb.connect ("localhost", "root", "nature101","citi")

cur = connection.cursor ()

sql1 = "select code from company"
cur.execute(sql1)
data1 = cur.fetchall()
data2 = list(data1)
print(data2)
c =len(data2)
print(c)
for a1 in data2:	
	#print("Enter a code")
	b = a1  
	print(b)
	query = " select Date,Open,High,Low,Last,Close,Total_Trade_Quantity,Turnover from stocks where Code = %(Code)s"
	cur.execute(query, {'Code' : b})
	data = cur.fetchall()
	#print(data)
	df = pd.DataFrame(list(data), columns=['Date','Open','High','Low','Last','Close','Total Trade Quantity','Turnover'])
	#print(df)

	df.to_csv('out.csv')

	data = pd.read_csv('out.csv', parse_dates=True, index_col='Date')
	data['Close'] = pd.to_numeric(data['Close'], errors='coerce')

	#data['LogClose'] = np.log(data[['Close']]) 

	df = pd.DataFrame({'ds':data.index, 'y':data.Close}) 


	#df['Close'] = pd.to_numeric(df['Close'], errors='coerce')

	m = Prophet()
	m.fit(df)

	future = m.make_future_dataframe(periods=1)      
	forecast = m.predict(future)
	forecast=forecast.drop(columns=['trend','trend_lower','trend_upper','yhat_lower','yhat_upper','seasonal','seasonal_lower','seasonal_upper'])
	#forecast=forecast.drop(columns=['seasonalities','seasonalities_lower','seasonalities_upper','weekly','weekly_lower'])
	#forecast=forecast.drop(forecast.index[:56])
	print(forecast)
	print("getting the last row")
	c = forecast.tail(2)
	print(c)

	k = c['yhat'] -c['yhat'].shift(-1)

	j= k.head(1)

	i = float(j)

	print(i)
	print(type(i))
	#pd.options.display.float_format = '{:.2f}'.format

	#print(j)
	#j.round(2)
	#print(j)
	#j=format(j, '.2f')
	#print("This is k now")
	#print(j)

	query = "update company set risk2 = %(risk2)s where Code = %(Code)s"
	cur.execute(query, {'risk2': i , 'Code' : b})
	connection.commit()

	


connection.close()






