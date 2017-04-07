import os
import math
from random import randint, random

class ActionSet:
    NOTHING = 0
    JUMP = 1

    def __init__(self):
        self.qJump = 1
        self.jCount = 0
        self.qNothing = 1
        self.nCount = 0
    
    #Returns the maximum reward associated with this state
    def max_reward(self):
        jumpR = self.qJump + 5.0/(self.jCount+1)
        nothR = self.qNothing + 5.0/(self.nCount+1)
        return max(jumpR, nothR)
    
    #Choose an action
    def choose_action(self, dx, dy):
        action = 0
        #If equal, jump if lower than pipe, else nothing
        if (self.qJump +5.0/(self.jCount+1)) == (self.qNothing +5.0/(self.nCount+1)):
            if dx > 30 and dy < -10:
                action = ActionSet.JUMP
            else:
                action = ActionSet.NOTHING
        elif self.qJump +5.0/(self.jCount+1) > self.qNothing +5.0/(self.nCount+1):
            action = ActionSet.JUMP
        else:
            action = ActionSet.NOTHING          
        
        return action
    def __str__(self):
        s = "Q Jump - " + str(self.qJump) + "\n" + "Q nothing - " + str(self.qNothing)
        return s
    #Return reward associated with specified action
    def get_reward(self, action):
        if action == ActionSet.JUMP:
            return self.qJump
        return self.qNothing
    
    #Update the Q value for the specified action and update counts 
    def update_reward(self, action, value):
        if action == ActionSet.JUMP:
            self.qJump = value
        else:
            self.qNothing = value
        if action == ActionSet.JUMP:
            self.jCount += 1
        else:
            self.nCount += 1  
   	
   	#This was a good number from trial and error
    def get_alpha(self):
        return .9
                
    
class Agent:
    
    def __init__(self):
        self.Q = Q_Array()   
             
     #Return an action
    def decide(self, state, bird):
        if not self.Q.contains(state):
            self.Q.add(state)
        actions = self.Q.get(state) 
        action = actions.choose_action(state.dx, state.dy)
        
        return action
    #Determine the immediate reward for the state transition
    def immediate_reward(self, newState, action, pipeCleared, pipeCollision):
        dist = math.sqrt(newState.dx**2 + newState.dy**2)
        #If bird died
        if newState.dead:
            self.Q.get(newState).update_reward(0, -1000)
            self.Q.get(newState).update_reward(1, -1000)
			#Base punishment
            r = -(1000 + dist)
			#If hit pipe, punish slightly more
            if pipeCollision:
                  r -= 2000
			#Heavy punishment for falling off map
            else:
                  r -= 10000;
            print("Died, punishing " + str(r))
            return r
        #Bird lives
        else:
            r = 1.0
            r += 15.0/dist
			#Reward for new pipe
            if pipeCleared:
                r += 8000
            return r
  
  	#Calculates Q(s,a) = qOld + alpha*(reward + qNewMax - qOld)
    def learn(self, oldState, newState, action, pipeCleared, pipeCollision):
        oldQActions = self.Q.get(oldState)
		
        if not self.Q.contains(newState):
            self.Q.add(newState)
			
        newQActions = self.Q.get(newState)
        r = self.immediate_reward(newState, action, pipeCleared, pipeCollision)
        alpha = oldQActions.get_alpha()
        qOld = oldQActions.get_reward(action)
        qNewMax = newQActions.max_reward()
       	#Gamme = .8
        qOld = qOld + alpha*(r + .8*qNewMax - qOld )
        
        oldQActions.update_reward(action, qOld)

#Scores, states, action pairs
class Q_Array:
    def __init__(self):
        self.history = {}
		
    #Adds new state with new ActionSet
    def add(self,state):
        if state not in self.history:
            self.history[state] = ActionSet()
			
    #Returns Actions associated with specified state
    def get(self,state):
        return self.history[state]
		
    #Check if state is in Q_Array
    def contains(self,state):
        return state in self.history

#Helper function
def my_round(x, base = 3):
    return int(base * round(float(x) / base))

#State representation
	#dx - difference of right side of pipe to the bird's x position 
	#dy - difference of pipe window height to bird y position
	#dead - whether or not the bird is dead
	#is_jumping - whether or not the bird is rising or falling
	#py - what third of the Y axis the pipe is in
class State:
    def __init__(self, dx, dy, dead, is_jumping,py):
        self.dx = int(my_round(dx))
        self.dy = int(my_round(dy))
        self.dead = dead
        self.is_jumping = is_jumping
        if py < (512 / 3):
            self.py = 0
        elif py < (512 / 2):
            self.py = 1
        elif py < 512:
            self.py = 2      
    def __eq__(self, other):
        if not isinstance(other, State):
            return False
        
        return self.py == other.py and self.is_jumping == other.is_jumping and self.dead == other.dead and self.dx == other.dx and self.dy == other.dy
    
    def __hash__(self):
        return hash((self.dx,self.dy,self.dead, self.is_jumping, self.py))
    
    def __str__(self):
        return str((self.dx,self.dy,self.dead, self.is_jumping, self.py))