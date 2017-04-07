//CS449 Project 1 Blackjack
//Atta Ebrahimi

#include <stdio.h>
#include <string.h>

//Prototypes

void print_dealer_hand(int dealer_hand[], int dh_total);
int deal_card(int total);
int sum_cards(int hand[], int num_cards);
void print_player_hand(int player_hand[], int ph_total);
void final_dealer_hand(int dealer_hand[], int dh_total, int dealer_sum);

int main(void)
{
	//Seed
	srand((unsigned int)time(NULL));
	
	//Holds random number
	int random_card;
	
	//Continues while false
	int busted = 0;
	
	//Running totals of player and dealer
	int dealer_sum = 0;
	int player_sum = 0;
	
	//Holds player and dealer's cards
	int dealer_hand[10];
	int player_hand[10];
	
	//Total number of cards in player and dealer hand
	int dh_total = 0;
	int ph_total = 0;
	
	//Deals two cards to player and dealer
	dealer_hand[0] = deal_card(dealer_sum);
	dealer_hand[1] = deal_card(dealer_sum);
	dh_total = 2;
	
	player_hand[0] = deal_card(player_sum);
	player_hand[1] = deal_card(player_sum);
	ph_total = 2;
	
	//choice array (holds user input)
	char choice[100];
	char hit[] = "hit";
	char stand[] = "stand";
	dealer_sum = sum_cards(dealer_hand, dh_total);
	print_dealer_hand(dealer_hand, dh_total);
	
	player_sum = sum_cards(player_hand, ph_total);
	print_player_hand(player_hand, ph_total);
	printf("\n\nYour total is: %d\n\n", player_sum);
	
	//Game will go until busted
	
	while(!busted)
	{

		printf("Would you like to \"hit\" or \"stand\"?: ");
		scanf("%s", choice);
		printf("\n");	

		//If user puts in something other than hit or stand, ask again
        while(strcmp(choice, "hit") != 0 && strcmp(choice, "stand") != 0)
		{
			printf("\nYou didn't choose \"hit\" or \"stand\", try again: ");
			scanf("%s", choice);
		}
		
		//If the player chose hit, they will receive another card
		if(strncmp(hit, choice, 3) == 0)
		{
			player_hand[ph_total] = deal_card(player_sum);
			ph_total++;
		}
		
		//If dealer sum is less than 18, dealer gets another card
		if(dealer_sum < 18)
		{
			dealer_hand[dh_total] = deal_card(dealer_sum);
			dh_total++;
		}
		
		dealer_sum = sum_cards(dealer_hand, dh_total);
		print_dealer_hand(dealer_hand, dh_total);
		
		player_sum = sum_cards(player_hand, ph_total);
		print_player_hand(player_hand, ph_total);
		printf("\n\nYour total is: %d\n\n", player_sum);
		
		//Conditions must be met if you win or lose, if none or met program loops to hit or miss
		if(dealer_sum == 21)
		{
			final_dealer_hand(dealer_hand, dh_total, dealer_sum);
			printf("\nDealer got blackjack, dealer wins!\n");
			busted = 1;
		}
		else if(player_sum == 21)
		{
			final_dealer_hand(dealer_hand, dh_total, dealer_sum);
			printf("\nPlayer got blackjack, dealer wins!\n");
			busted = 1;
		}
		else if(player_sum > 21 && dealer_sum < 21)
		{
			final_dealer_hand(dealer_hand, dh_total, dealer_sum);
			printf("\nYou busted. Dealer wins!\n");
			busted = 1;
		}

		else if(player_sum < 21 && dealer_sum > 21)
		{
			final_dealer_hand(dealer_hand, dh_total, dealer_sum);
			printf("\nDealer busted. You win!\n");
			busted = 1;
		}
		else if(dealer_sum >= 18 && player_sum >= 18)
		{
			
			int closer1 = 21 - dealer_sum;
			int closer2 = 21 - player_sum;
			
			final_dealer_hand(dealer_hand, dh_total, dealer_sum);
			
			if(closer1 == closer2)
			{
				printf("\nThe game results in a draw!\n");
				busted = 1;
			}
			else if(closer1 > closer2)
			{
				printf("\nPlayer is closer, player wins!\n");
				busted = 1;
			}

			else{
				printf("\nDealer is closer, dealer wins!\n");
				busted = 1;
			}
		}
	}
}

/*
*Generates random card from 1-10
*Numbers 11-13 represent Jack, Queen, and King and are converted to 10
*/
int deal_card(int total)
{
	int random_card = (rand() % 13) + 1;
	
		if(random_card == 11 || random_card == 12 || random_card == 13)
		{
			random_card = 10;
		}
		
		if(random_card == 1){
			if((total + 11) < 21)
			{
				random_card = 11;
			}
		}
		
		
	
	return random_card;
}

/*
*Prints the dealer's hand, first card is unknown
*/
void print_dealer_hand(int dealer_hand[], int dh_total)
{
	int card = dealer_hand[dh_total - 1];
	
	printf("The dealer:\n\t? + %d", dealer_hand[1]);
	
		int i = 0;
		for(i = 2; i < dh_total; i++)
		{
			printf(" + %d", dealer_hand[i]);
		}
}

//Prints player's cards
void print_player_hand(int player_hand[], int ph_total)
{
	printf("\nYou:\n\t%d", player_hand[0]);
		int i = 0;
		for(i = 1; i < ph_total; i++)
		{
			printf(" + %d", player_hand[i]);
		}
}

/*
*Sums player's cards, if there is an ace, it is always counted as 11 unless
*the total would exceed 21
*/
int sum_cards(int hand[], int num_cards){
	int sum = 0, num_of_aces = 0, i = 0;
	
		for(i = 0; i < num_cards; i++)
		{
			if(hand[i] == 1)
			{
				num_of_aces++;
				sum = sum + 11;
			}
			else
			{
				sum = sum + hand[i];
			}
		}
		
		while(sum > 21 && num_of_aces > 0)
		{
			sum = sum - 10;
			num_of_aces--;
		}
	
	return sum;
}

//Prints dealer's final hand
void final_dealer_hand(int dealer_hand[], int dh_total, int dealer_sum)
{
	printf("\nDealer's final hand:\n\t%d", dealer_hand[0]);
		int i = 0;
		for(i = 1; i < dh_total; i++)
		{
			printf(" + %d", dealer_hand[i]);
		}
		
		printf(" = %d", dealer_sum);
}

