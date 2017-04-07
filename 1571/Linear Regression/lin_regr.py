import csv
import sys
import ast
import copy

def modelCalc(W, X):
	hx = 0.0
	lengthOfX = X.__len__()
	
	for i in range(0, lengthOfX):
		hx = hx + X[i] * W[i]
	return hx
		
def gradientDescent(S, alpha):
	wZero = 0.0
	wOne = 0.0
	
	iterations = 0
	convergenceDifference = .001
	
	while (True):
		iterations = iterations + 1
		dwZero = 0.0
		dwOne = 0.0
		
		for example in S:
			xi = example[0]
			yi = example[1]
		
			hx = wZero + wOne*xi
			
			dwZero = dwZero + (yi - hx)
			dwOne = dwOne + (yi - hx) * xi
			
			
		tempWZero = wZero + (alpha/iterations)*dwZero
		tempWOne = wOne + (alpha/iterations)*dwOne
		
		diffWZero = abs(tempWZero - wZero)
		diffWOne = abs(tempWOne - wOne)
		
		if (diffWZero < convergenceDifference and diffWOne < convergenceDifference):
			wZero = tempWZero
			wOne = tempWOne
			break
		if (iterations%5 == 0):
			print("The current model has w0 = %f and w1 = %f" % (wZero, wOne))
			
		wZero = tempWZero
		wOne = tempWOne
		
	return [wZero, wOne]

def multiGradientDescent(S, alpha):
	numberOfFeatures = S[0].__len__() - 1
	W = []
	
	for i in range(0, numberOfFeatures+1):
		W.append(0.0)
		
	convergenceDifference = .001
	iterations = 0.0
	while True:
		iterations = iterations + 1
		if iterations == 1000:
			break
		differenceW = []
		
		for i in range(0, numberOfFeatures+1):
			differenceW.append(0.0)
		
		for example in S:
			X = [1.0]
			
			lengthOfEx = example.__len__()
			for i in range(1, lengthOfEx):
				X.append(example[i - 1])
			y = example[lengthOfEx-1]
			hx = modelCalc(W, X)
		
			for i in range(0, X.__len__()):
				differenceW[i] = differenceW[i] + (y - hx)*X[i]
		
		tempW = []
		for i in range(0, W.__len__()):
			tempW.append(W[i] + (alpha/iterations) * differenceW[i])
			
		convergence = True
		for i in range(0, W.__len__()):
			if abs(W[i] - tempW[i]) >= convergenceDifference:
				convergence = False
				break
		W = copy.deepcopy(tempW)
		
		if convergence:
			break
	return W
	
def calcMSE(S, W):
	X = [1.0]
	
	error = []
	
	for v in S:
		featLen = v.__len__()-1
		X = [1.0]
		
		for i in range(0, featLen):
			X.append(v[i])
		y = v[featLen]
		
		prediction = modelCalc(W, X)
		
		error.append((prediction-y)**2)
		
	MSE = sum(error)/len(error)
	
	return MSE
			
if __name__ == '__main__':
	trainingFile = sys.argv[1]
	testFile = sys.argv[2]
	
	train = []
	test = []
	
	with open(trainingFile, 'r') as f:
		for line in f:
			train.append(ast.literal_eval(line))
	
	with open(testFile, 'r') as f:
		for line in f:
			test.append(ast.literal_eval(line))
#Part 1
	#Training
	alpha = .232
	W = gradientDescent(train, alpha)
	print("The value of w0 is: %f and the value of w1 is: %f" % (W[0], W[1]))
	
	#Testing
	MSE = 0
	lengthOfTest = test.__len__()
	for case in test:
		hx = W[0] + W[1]*case[0]
		MSE = MSE + (hx - case[1])**2
	
	MSE = MSE/lengthOfTest
	print("The MSE = %f" % MSE)
	
#Part 2
	data = []
	with open('part2.csv', 'r') as f:
		for line in f:
			data.append(ast.literal_eval(line))
	data = [list(elem) for elem in data]
		
	trainingSize = int(.8*(data.__len__() - 1))
	
	train = copy.deepcopy(data[0:trainingSize])
	test = copy.deepcopy(data[trainingSize:(data.__len__() - 1)])
	print()
	print("The regular dataset")
	alpha = .0005
	W = multiGradientDescent(train, alpha)
	print("The w values are: ")
	print(W)
	error = calcMSE(test, W)
	print("The error is: ")
	print(error)
	
	print()
	
	#Normalizing dataset
	norm_data = copy.deepcopy(data)
	for entry in norm_data:
		features = entry.__len__()-1
		total = 0
		for i in range(0,features):
			total = total + entry[i]
		for j in range(0,features):
			entry[j] = entry[j]/total
	
	train = copy.deepcopy(norm_data[0:trainingSize])
	test = copy.deepcopy(norm_data[trainingSize:(norm_data.__len__() - 1)])
	
	print("The normalized dataset")
	alpha = .0005
	W = multiGradientDescent(train, alpha)
	print("The w values are: ")
	print(W)
	error = calcMSE(test, W)
	print("The error is: ")
	print(error)
	
	print()
	print("The normalized dataset with travel time, study time, family relationships popped")
	#Popping values that I deemed redundant from the normalized set
	norm_and_popped_data = copy.deepcopy(data)
	for entry in norm_and_popped_data:
		#print(entry)
		entry.pop(6)
		entry.pop(4)
		entry.pop(3)
	for entry in norm_and_popped_data:
		features = entry.__len__()-1
		total = 0
		for i in range(0,features):
			total = total + entry[i]
		for j in range(0,features):
			entry[j] = entry[j]/total
	
	train = copy.deepcopy(norm_and_popped_data[0:trainingSize])
	test = copy.deepcopy(norm_and_popped_data[trainingSize:(norm_and_popped_data.__len__() - 1)])
	alpha = .0005
	W = multiGradientDescent(train, alpha)
	print("The w values are: ")
	print(W)
	error = calcMSE(test, W)
	print("The error is: ")
	print(error)