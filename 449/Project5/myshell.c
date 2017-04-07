//Atta Ebrahimi

#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

char user_input[200];
char *token;
const char space[] = " ";
char t1[200];
char t6[200];
char t2[200];
char t7[200];
char t3[200];
char t8[200];
char t4[200];
char t9[200];
char t5[200];
char t10[200];

char *temparray[2000];
int token_num=0;
char *token_list[10]={t1,t2,t3,t4,t5,t6,t7,t8,t9,t10};
int IO = 0;
FILE *fp;
char* filename;
void switch_dir(char* dir){
	int status;
	char cwd[200];
	if(dir==NULL){
		printf("No such file or directory\n");
		return;
	}
	
	if(*dir=='/'){
		status=chdir((const char *)dir);
		if(status==-1){
			printf("No such file or directory\n");	
			}else{
			getcwd(cwd, 200);
			printf("Directory has been changed to: %s\n",cwd);	
		}
		
		}else{
		getcwd(cwd, 200);
		
		strcat(cwd,"/");
		strcat(cwd,dir);
		
		status=chdir((const char *)cwd);
		if(status==-1){
			printf("No such file or directory\n");	
			}else{
			getcwd(cwd, 200);
			printf("Directory has been changed to: %s\n",cwd);	
		}
	}
	
}


void do_unix_command(){
	if(fork()==0)
	{			
		(temparray[token_num])=NULL;
		
		if(IO==2){fp = freopen(filename, "a", stdout);}
		else if(IO==3){fp = freopen(filename, "w", stdout);}
		else if(IO==1){fp = freopen(filename, "r", stdin);}
		else{
			if(fp!=NULL){
				fclose(fp);
				}
			}

		execvp(temparray[0], temparray);
		
	}
	else
	{
		int status;
		wait(&status);
	}
}

int main(){
	char *tempStr;
	while(1){
		IO=0;
		token_num=0;
		printf("myshell $ ");
		fgets(user_input, 200, stdin);
		user_input[strlen(user_input)-1]=0x00;
		if(strcmp(user_input,"exit")==0)
		{			
			exit(0);
		}else {
			token= strtok(user_input, space);
			while(token!=NULL){

				if(strcmp(token,">>")==0){
					IO=2;				
				}
				else if(strcmp(token,"<")==0){
					IO=1;
				}
				else if(strcmp(token,">")==0){
					IO=3;
				}
				
				strncpy(token_list[token_num++],token,200);				
				token= strtok(NULL, space);				
			}
			
			
			if(IO!=0){filename=token_list[token_num-1];token_num=token_num-2;}
			
			if(strcmp(t1,"cd")==0){		
				switch_dir(t2);
			}
			else{
				memcpy(temparray, token_list, sizeof(token_list));
				do_unix_command();				
			}
		}
	}
	return 0;
}