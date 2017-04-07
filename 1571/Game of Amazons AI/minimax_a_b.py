import sys
import ast
from math import inf

def successor_states(state):
	if isinstance(state, list):
		return state[1:]
	else:
		return None
		
def max_value(state, alpha, beta):
	num_visited = 1
	
	if isinstance(state, tuple):
		(name, value) = state
		return (value, num_visited)
			
	value = -inf
	for s in successor_states(state):
		(minimum_from_state, visited_at_state) = min_value(s, alpha, beta)
		num_visited += visited_at_state
		value = max(value, minimum_from_state)
		if beta <= alpha:
			return value
		if value >= beta:
			return (value, num_visited)
		else:
			alpha = max(alpha, value)
	return (value, num_visited)
	
def min_value(state, alpha, beta):
	num_visited = 1
	
	if isinstance(state, tuple):
		(name, value) = state
		return (value, num_visited)
		
	value = inf
	for s in successor_states(state):	
		(maximum_from_state, visited_at_state) = max_value(s, alpha, beta)
		num_visited += visited_at_state
		value = min(value, maximum_from_state)
		if beta <= alpha:
			return value
		if value <= alpha:
			return (value, num_visited)
		else:
			beta = min(beta, value)
	return (value, num_visited)
	
def minimax_a_b(state):
    return max_value(state, -inf, inf)
	
if __name__ == '__main__':
	if sys.argv.__len__() < 2:
		print("Not enough arguments.")
	else:
		config_file_name = sys.argv[1]
	
	config_file = open(config_file_name, "r")
	
	tree = ast.literal_eval(config_file.readline())
	
	config_file.close
	
	(value, visited) = minimax_a_b(tree)
	print('Utility value of root:   %s' % value)
	print('Number of nodes visited: %s' % visited)