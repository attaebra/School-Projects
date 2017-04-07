#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>


// Declare function prototypes
char dice_roll(void);
void dice_display(void);
void dice_reroll(int num_to_reroll);
void score_out(void);
int compare_qsort (const void *num1, const void *num2);
int sum_dice(void);


int dice_driver; 									//Contains dice driver
int upper_section = 0;								//Contains user's upper section bonus if the upper section sum is greater than 62
char dice[5] = {0,0,0,0,0};							//Contains the player's dice	
int total_score = 0; 								//Contains the user's total score
int upper_scores[6] = {-1,-1,-1,-1,-1,-1};			//Contains user's scores for upper section
int lower_scores[7] = {-1,-1,-1,-1,-1,-1,-1};		//Contains user's scores for lower section


int main(void)
{
	dice_driver = open("/dev/dice", O_RDONLY);
	if (dice_driver == NULL) 
    {
    	printf("ERROR OPENING DRIVER.\n");
        return 1;
    }

	int choices[13] = {0};			//Contains the player's choices
	int num_choices = 0;			//Contains the number of choices player has made. 
	int num_to_reroll;				//Contains the number of dice user wants to reroll
	int upper_lower;				//Contains the user's selection for upper or lower
	int select_upper = 0;			//Contains the user's selection for the upper section
	int select_lower = 0;			//Containsthe user's selection for the lower section

	//Continue the game while the player has not made all choices
	while( num_choices < 14)
	{
		printf("\nYour roll: \n");

		//Roll 5 dice
		int i;
		for(i = 0;  i < 5 ; i = i + 1)
		{
			dice[i] = dice_roll();
		}

		dice_display();

		//Get num dice user wants to reroll 
		printf("\nWhich dice to reroll? (1 to 5)   ");
		scanf(" %d",&num_to_reroll);
		while(num_to_reroll < 0 || num_to_reroll > 5)
		{
			printf("ERROR: How many dice do you want to reroll? Enter an integer in the range 0 to 5.   ");
			scanf(" %d",&num_to_reroll);
		}

		// Reroll
		if(num_to_reroll != 0)
		{
			dice_reroll(num_to_reroll);
			printf("\nYour second roll:\n");
			dice_display();

			// Get num dice user wants to reroll 
			printf("\nWhich dice to reroll?(0 to 5)   ");
			scanf(" %d",&num_to_reroll);
			while(num_to_reroll < 0 || num_to_reroll > 5)
			{
				printf("ERROR: How many dice do you want to reroll? Enter an integer in the range 0 to 5.   ");
				scanf(" %d",&num_to_reroll);
			}

			if(num_to_reroll !=0)
			{
				dice_reroll(num_to_reroll);
				printf("\nYour third roll:\n");
				dice_display();
			}
		}																				
		
		// Ask user which category they'd like to place their points
		printf("\nPlace dice into:\n1) Upper Section\n2) Lower Section\n");
		printf("Selection? (1 or 2):   \n" );
		scanf(" %d",&upper_lower);	 

		while(upper_lower != 1 && upper_lower !=2)
		{
			printf("ERROR: Selection? Enter 1 or 2:   \n" );
			scanf(" %d",&upper_lower);	
		}

		/*For this choice, a user will select 1,2,3,4,5,or 6. Based on the choice
		 *we will sum how many dice they have that correspond to their choice, and then
		 *update the user score accordingly. 
		*/
		if(upper_lower == 1)
		{
			printf("\nPlace dice into:\n");
			printf("1) Ones\n2) Twos\n3) Threes\n4) Fours\n5) Fives\n6) Sixes\n");
			printf("If you don't have any dice of the selection you make, you'll be given a 0 for that category.\n");
			printf("Selection? Enter a number from 1 to 6:   ");
			scanf(" %d",&select_upper);
			while(select_upper < 1 || select_upper > 6)
			{
				printf("ERROR: Selection? Enter a number from 1 to 6:   \n");
				scanf(" %d",&select_upper);
			}
			
			if(upper_scores[select_upper-1] == -1)
			{
				// Sum the user's dice based on selection and update scores
				int m;
				for(m = 0; m < 5; m = m + 1)
				{
					if(upper_scores[select_upper-1] == -1)
					{
						upper_scores[select_upper-1] = 0;
					}

					if(dice[m] == select_upper)
					{
						total_score = total_score + select_upper;
						upper_scores[select_upper - 1] = upper_scores[select_upper - 1] + select_upper;
					}
				}
			}

		}
		else
		{
			printf("\nPlace dice into:\n");
			printf("1) Three of a Kind\n2) Four of a Kind\n3) Full House\n4) Small straight\n5) Large straight\n6) Yahtzee\n7) Change\n");
			printf("If you don't have the correct dice for a specific category you pick, you'll be given a 0 for that category.\n");
			printf("Selection? Enter a number from 1 to 7:   ");
			scanf(" %d",&select_lower);
			while(select_lower < 1 || select_lower > 7)
			{
				printf("ERROR: Selection? Enter a number from 1 to 7:   \n");
				scanf(" %d",&select_lower);
			}


			//DEBUG
			printf("\n\nUNSORTED DICE: [%d,%d,%d,%d,%d]\n\n",dice[0],dice[1],dice[2],dice[3],dice[4]);


			//Sort the dice 
			qsort(dice,5,sizeof(char),compare_qsort);


			//DEBUG
			printf("\n\nSORTED DICE: [%d,%d,%d,%d,%d]\n\n",dice[0],dice[1],dice[2],dice[3],dice[4]);



			//Appropriates score based on selection
			switch(select_lower)
			{
				case 1: //Three of a kind
				{
					if( (dice[0]==dice[1] && dice[1]==dice[2]) || 
						(dice[1]==dice[2] && dice[2]==dice[3]) ||
						(dice[2]==dice[3] && dice[3]==dice[4]) )
					{
						// If player already has a score for this category, don't update it
						if(lower_scores[0] == -1)
						{
							total_score = total_score + sum_dice();
							lower_scores[0] = sum_dice();
						}
					}
					else //If they pick this category but they don't meet the condition, give them 0 points
					{
						lower_scores[0] = 0;
					}
					break;
				}
				case 2: //Four of a kind
				{
					if( ((dice[0]==dice[1] && dice[1]==dice[2]) && (dice[2]==dice[3]))  || 
						((dice[1]==dice[2] && dice[2]==dice[3]) && (dice[3]==dice[4])) )
					{
						//If player already has a score for this category do not update
						if(lower_scores[1] == -1)
						{		
							total_score = total_score + sum_dice();
							lower_scores[1] = sum_dice();	
						}
					}
					else //If they pick this category but they don't meet the condition give 0 points
					{
						lower_scores[1] = 0;
					}
					break;
				}
				case 3: //Full house
				{
					if( (dice[0]==dice[1] && (dice[2]==dice[3] && dice[3]==dice[4])) ||
						((dice[0]==dice[1] && dice[1]==dice[2]) && dice[3]==dice[4]) )
					{
						//If player already has a score for this category do not update
						if(lower_scores[2] == -1)
						{
							total_score = total_score + 25;
							lower_scores[2] = 25;
						}
					}
					else //If they pick this category but they don't meet the condition give 0 points
					{
						lower_scores[2] = 0;
					}
					break;
				}
				case 4: //Small straight
				{
					if( ((dice[1]==dice[0]+1) && (dice[2]==dice[0]+2) && (dice[3]==dice[0]+3)) ||
						((dice[2]==dice[1]+1) && (dice[3]==dice[1]+2) && (dice[4]==dice[1]+3)) )
					{
						// If player already has a score for this category do not update
						if(lower_scores[3] == -1)
						{
							total_score = total_score + 30;
							lower_scores[3] = 30;
						}
					}
					else //If they pick this category but they don't meet the condition give 0 points
					{
						lower_scores[3] = 0;
					}
					break;
				}
				case 5: //Large straight
				{
					if( (dice[1]==dice[0]+1) && (dice[2]==dice[0]+2) && (dice[3]==dice[0]+3) && (dice[4]==dice[0]+4) )
					{
						// If player already has a score for this category do not update
						if(lower_scores[4] == -1)
						{
							total_score = total_score + 40;
							lower_scores[4] = 40;
						}
					}
					else //If they pick this category but they don't meet the condition give 0 points
					{
						lower_scores[4] = 0;
					}
					break;
				}
				case 6: //Yahtzee
				{	
					if(dice[0]==dice[1] && dice[1]==dice[2] && dice[2]==dice[3] && dice[3]==dice[4])
					{
						//If player already has a score for this category do not update
						if(lower_scores[5] == -1)
						{
							total_score = total_score + 50;
							lower_scores[5] = 50;
						}
					}
					else //If they pick this category but they don't meet the condition, give 0 points
					{
						lower_scores[5] = 0;
					}
					break;
				}
				default: //Chance
				{	
					if(lower_scores[6] == -1)
					{
						total_score = total_score + sum_dice();
						lower_scores[6] = sum_dice();
					}
					else //If they pick this category but they don't meet the condition, give 0 points
					{
						lower_scores[6] = 0;
					}
					break;
				}
			}
		} 


		//Add bonus if upper section points are greater than or equal to 63
		if(sum_dice() > 62)
		{
			if(upper_section == 0)
			{
				upper_section = 35;
				total_score = total_score + 35;
			}
		} 
		score_out();

		num_choices = num_choices + 1;
	}


}


// This function will generate dice rolls and pass a byte value of 0 to 5 to main.
char dice_roll(void)
{	
	unsigned char randbyte;

	// generate rand byte from 0 to 5
	read(dice_driver,&randbyte,1);

	return randbyte + 1;
}


// This funciton display's the user's die to the console
void dice_display(void)
{
	printf("%d %d %d %d %d\n",dice[0],dice[1],dice[2],dice[3],dice[4]);
}


// This function will reroll the user's dice
void dice_reroll(int num_to_reroll)
{
	int dice_index;
	int j;
	for(j = 0; j < num_to_reroll; j = j + 1)
	{
		switch (j)
		{
			case 0:
			{
				printf("What is the first dice you want to reroll?   ");
				scanf(" %d",&dice_index);
	
				dice_index = dice_index - 1;
				while(dice_index < 0 || dice_index > 5)
				{
					printf("ERROR: What is the first dice you want to reroll?   ");
					scanf(" %d",&dice_index);
				}

				dice[dice_index] = dice_roll();
				break;		
			}
			case 1:
			{
				printf("What is the second dice you want to reroll?   ");
				scanf(" %d",&dice_index);


				dice_index = dice_index - 1;
				while(dice_index < 0 || dice_index > 5)
				{
					printf("ERROR: What is the second dice you want to reroll?   ");
					scanf(" %d",&dice_index);
				}

				dice[dice_index] = dice_roll();
				break;		
			}
			case 2:
			{
				printf("What is the third dice you want to reroll?   ");
				scanf(" %d",&dice_index);

				dice_index = dice_index - 1;
				while(dice_index < 0 || dice_index > 5)
				{
					printf("ERROR: What is the third dice you want to reroll?   ");
					scanf(" %d",&dice_index);
				}

				dice[dice_index] = dice_roll();
				break;		
			}
			case 3:
			{
				printf("What is the fourth dice you want to reroll?   ");
				scanf(" %d",&dice_index);

				dice_index = dice_index - 1;
				while(dice_index < 0 || dice_index > 5)
				{
					printf("ERROR: What is the fourth dice you want to reroll?   ");
					scanf(" %d",&dice_index);
				}

				dice[dice_index] = dice_roll();
				break;		
			}
			default:
			{
				printf("What is the fifth dice you want to reroll?   ");
				scanf(" %d",&dice_index);

				dice_index = dice_index - 1;
				while(dice_index < 0 || dice_index > 5)
				{
					printf("INPUT ERROR: What is the fifth dice you want to reroll?   ");
					scanf(" %d",&dice_index);
				}

				dice[dice_index] = dice_roll();
				break;		
			}
		}
	}	
}


// This function will display the user's score and the score tables
void score_out(void)
{
	printf("\nNOTE: -1 represents categories you have not chosen yet.\n");
	printf("Your score so far is: %d\n",total_score);
	printf("\nOnes: %d\t\tFours: %d\nTwos: %d\t\tFives: %d\nThrees: %d\t\tSixes: %d\n",
			upper_scores[0],upper_scores[3],upper_scores[1],upper_scores[4],upper_scores[2],upper_scores[5]);
	
	printf("Upper section bonus: %d\n",upper_section);
	printf("Three of a Kind: %d\tFour of a Kind: %d\nSmall Straight: %d\tLarge Straight:%d\nFull House: %d\t\tYahtzee: %d\nChance: %d\n",
		lower_scores[0],lower_scores[1],lower_scores[3],lower_scores[4],lower_scores[2],lower_scores[5],lower_scores[6]);
}


int compare_qsort (const void * a, const void * b)
{
    if( *(char *)a == *(char *)b) return 0;
    if( *(char *)a > *(char *)b) return 1;
    if( *(char *)a < *(char *)b) return -1;
}

// This function will return the sum of the dice
int sum_dice(void)
{
	return dice[0] + dice[1] + dice[2] + dice[3] + dice[4];
}