import pandas as pd
import matplotlib.pyplot as plt
import numpy as np 
import math
import MySQLdb
from datetime import datetime
from sklearn.preprocessing import MinMaxScaler
from sklearn.metrics import mean_squared_error
from keras.models import Sequential
from keras.layers import Dense, Activation
from keras.layers import LSTM


def new_dataset(dataset, step_size):
	data_X, data_Y = [], []
	for i in range(len(dataset)-step_size-1):
		a = dataset[i:(i+step_size), 0]
		data_X.append(a)
		data_Y.append(dataset[i + step_size, 0])
	return np.array(data_X), np.array(data_Y)

connection = MySQLdb.connect ("localhost", "root", "nature101","citi")

cur = connection.cursor ()

sql1 = "select code from company"
cur.execute(sql1)
data1 = cur.fetchall()
data2 = list(data1)

for a1 in data2:	
	#print("Enter a code")
	b = a1  
	print(b)
	query = " select Open,High,Low,Close from stocks where Code = %(Code)s"
	cur.execute(query, {'Code' : b})
	data = cur.fetchall()
	
	df = pd.DataFrame(list(data), columns=['Open','High','Low','Close',])
	df.to_csv('out1.csv')
	df = pd.read_csv('out1.csv')
	
	OHLC_avg = df.mean(axis = 1)
	HLC_avg = df[['High', 'Low', 'Close']].mean(axis = 1)
	close_val = df[['Close']]

	OHLC_avg = np.reshape(OHLC_avg.values, (len(OHLC_avg),1)) # 1664
	scaler = MinMaxScaler(feature_range=(0, 1))
	OHLC_avg = scaler.fit_transform(OHLC_avg)
	
	train_OHLC = int(len(OHLC_avg) * 0.75)
	test_OHLC = len(OHLC_avg) - train_OHLC
	train_OHLC, test_OHLC = OHLC_avg[0:train_OHLC,:], OHLC_avg[train_OHLC:len(OHLC_avg),:]

	trainX, trainY = new_dataset(train_OHLC, 1)
	testX, testY = new_dataset(test_OHLC, 1)

	trainX = np.reshape(trainX, (trainX.shape[0], 1, trainX.shape[1]))
	testX = np.reshape(testX, (testX.shape[0], 1, testX.shape[1]))
	step_size = 1

	model = Sequential()
	model.add(LSTM(32, input_shape=(1, step_size), return_sequences = True))
	model.add(LSTM(16))
	model.add(Dense(1))
	model.add(Activation('sigmoid'))

	model.compile(loss='mean_absolute_error', optimizer='adagrad') # Try SGD, adam, adagrad and compare!!!
	model.fit(trainX, trainY, epochs=5, batch_size=1, verbose=2)

	trainPredict = model.predict(trainX)
	testPredict = model.predict(testX)
	testPredict = scaler.inverse_transform(testPredict)

	last_val = testPredict[-1]
	last_val_scaled = last_val/last_val
    #print(last_val_scaled)
	next_val = model.predict(np.reshape(last_val_scaled, (1,1,1)))
	print "Last Day Value:", np.asscalar(last_val)
	print "Next Day Value:", np.asscalar(last_val*next_val)
	k = np.asscalar(last_val) 
	j = np.asscalar(last_val*next_val)
    
	i = j-k;	

	query = "update company set risk4 = %(risk4)s where Code = %(Code)s"
	cur.execute(query, {'risk4': i , 'Code' : b})
	connection.commit()

connection.close()
	
