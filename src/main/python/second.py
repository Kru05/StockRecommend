
import numpy as np
import pandas as pd
import quandl
from keras.models import Sequential
from keras.layers import Dense
from keras.layers import LSTM
from sklearn.preprocessing import MinMaxScaler

quandl.ApiConfig.api_key = "y2x5Q3grshy55LrQsQim"
df = quandl.get("NSE/HDFC", start_date="1998-03-23",end_date="2017-12-10")

test_set = quandl.get("NSE/HDFC", start_date="2017-12-10",end_date="2018-02-06")


df = df.iloc[:,4:5]
df = df.values

sc = MinMaxScaler()
df = sc.fit_transform(df)


X_train = df[0:4919]
print(X_train)
y_train = df[0:4919]
print(y_train)


X_train = np.reshape(X_train, (4919, 1, 1))


regressor = Sequential()
regressor.add(LSTM(units = 4, activation = 'sigmoid', input_shape = (None, 1)))
regressor.add(Dense(units = 1))
regressor.compile(optimizer = 'adam', loss = 'mean_squared_error')
regressor.fit(X_train, y_train, batch_size = 200, epochs = 200)

real_stock_price = test_set.iloc[1:,4:5]
real_stock_price = real_stock_price.values

inputs = real_stock_price
inputs = sc.transform(inputs)
inputs = np.reshape(inputs, (37, 1, 1))
predicted_stock_price = regressor.predict(inputs)
predicted_stock_price = sc.inverse_transform(predicted_stock_price)
print("Predicted value",predicted_stock_price)


