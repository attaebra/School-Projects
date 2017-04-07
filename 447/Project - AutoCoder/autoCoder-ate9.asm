#
# Atta Ebrahimi
# ate9@pitt.edu
#		
	.data
welcome: 	.asciiz 	"Welcome to Auto Coder!\nThe opcode (1-9 : 1=add, 2=addi, 3=or, 4=ori, 5=lw, 6=sw, 7=j, 8=beq, 9=bne)\n"
assembly:	.asciiz		"\n\nThe completed code is\n"
machine:	.asciiz		"\n\nThe machine code is\n"
prompt1: 	.asciiz 	"please enter the 1st opcode: "
prompt2: 	.asciiz 	"please enter the 2nd opcode: "
prompt3: 	.asciiz 	"please enter the 3rd opcode: "
prompt4: 	.asciiz 	"please enter the 4th opcode: "
prompt5: 	.asciiz 	"please enter the 5th opcode: "
labels: 	.asciiz 	"L099\0 L100\0 L101\0 L102\0 L103\0 L104"
operations:	.asciiz		"add \0 addi \0or \0  ori \0 lw \0  sw \0  j \0   beq \0 bne "
registers: 	.asciiz 	"$t0\0$t1\0$t2\0$t3\0$t4\0$t5\0$t6\0$t7\0$t8\0$t9\0$t0"
constants:	.asciiz		"100\0101\0102\0103\0104"
tab:		.asciiz		"\t"
new_line:	.asciiz		"\n"
colon:		.asciiz		":"
comma: 		.asciiz 	", "
open_p:		.asciiz		"("
close_p:	.asciiz		")"
error_message:	.asciiz		" causes error: opcode is not an integer 1-9"
array: 		.space 25 	# data array (5x5)
input1: 	.byte
input2: 	.byte
input3: 	.byte
input4: 	.byte
input5: 	.byte


	.text

	la $a0, welcome
	li $v0, 4
	syscall 		# print welcome statement
	jal initialize_array	# sets data of array to all 0's
	jal input_prompt	# saves 5 "op code" inputs of integers 1 to 9
	jal set_operations	# sets operations of array data to input data
	jal develop_data	# fills in incomplete data of array
	la $a0, assembly
	li $v0, 4
	syscall 		# print assembly statement
	jal print_assembly_code	# processes (but does not change) data array and prints data as assembly code
	la $a0, machine
	li $v0, 4
	syscall 		# print machine statement
	jal print_machine_code	# processes (but does not change) data array and prints machine code
	j terminate		# terminates program

initialize_array:
	li $t0, 0		# iteration
	la $t1, array		# address of array
initialize_array_loop:
	sb $zero, ($t1)		# save 0 into byte
	addi $t0, $t0, 1	# iteration++
	addi $t1, $t1, 1	# address of array++
	beq $t0, 25, return	# quit loop if iterated 25 times
	j initialize_array_loop

input_prompt: 			# prompts user int input, loops 5 times
	li $t0, 0 		# input iteration starts at 0, goes to 4. At 5, stops loop
	la $t1, input1 		# input addressing starts at input1
	la $t2, prompt1		# prompt addressing starts at prompt1
input_loop:
	move $a0, $t2 		# prompt address to $a0
	addi $v0, $zero, 4	# code for printing string
	syscall 		# print prompt statement
	addi $v0, $zero, 5 	# code for reading int
	syscall 		# read integer
	sb $v0, ($t1) 		# save int to address + offset
	addi $t0, $t0, 1 	# iteration++
	addi $t1, $t1, 1 	# input address++
	addi $t2, $t2, 30 	# prompt address++
	beq $t0, 5, return	# if 5th iteration done, quit loop
	j input_loop

set_operations:
	li $t0, 0		# iteration
	la $t1, input1		# address of input iteration
	la $t2, array		# address of array, starting at iteration 0's operation
	addi $t2, $t2, 1
set_operations_loop:
	lb $t3, ($t1)		# get iteration's input
	sb $t3, ($t2)		# save input to iterations array byte
	addi $t0, $t0, 1	# iteration++
	addi $t1, $t1, 1	# input address++
	addi $t2, $t2, 5 	# array row++ (to next iteration operation byte)
	beq $t0, 5, return	# if 5th iteration done, quit loop
	j set_operations_loop
	
develop_data:			# 1=add, 2=addi, 3=or, 4=ori, 5=lw, 6=sw, 7=j, 8=beq, 9=bne
	li $t0, 0		# iteration (for generality and labels)
	li $t1, 0		# registers iteration
	li $t2, 0		# constants iteration
	la $t3, array		# address of array, starting at iteration 0's operation
	addi $t3, $t3, 1
develop_data_loop:
	lb $t4, ($t3) 		# iteration's operation
	
	beq $t4, 1, develop_add
	beq $t4, 2, develop_addi
	beq $t4, 3, develop_or
	beq $t4, 4, develop_ori
	beq $t4, 5, develop_lw
	beq $t4, 6, develop_sw
	beq $t4, 7, develop_j
	beq $t4, 8, develop_beq
	beq $t4, 9, develop_bne
	lb $a0, ($t3)
	li $v0, 1
	syscall
	j error

develop_add:			# array: (label, operation, $d, $s, $t)
	addi $t3, $t3, 2	# $s address
	sb $t1, ($t3)		# save register
	addi $t1, $t1, 1	# register++
	addi $t3, $t3, 1	# $t address
	sb $t1, ($t3)		# save register
	addi $t1, $t1, 1	# register++
	addi $t3, $t3, -2	# $d address
	sb $t1, ($t3)		# save register (no register++)
	addi $t3, $t3, -1	# return to iteration's operation
	j develop_data_end

develop_addi:			# array: (label, operation, $t, $s, imm)
	addi $t3, $t3, 2	# $s address
	sb $t1, ($t3)		# save register
	addi $t1, $t1, 1	# register++
	addi $t3, $t3, 1	# imm address
	sb $t2, ($t3)		# save constant
	addi $t2, $t2, 1	# constant++
	addi $t3, $t3, -2	# $t address
	sb $t1, ($t3)		# save register (no register++)
	addi $t3, $t3, -1	# return to iteration's operation
	j develop_data_end
	
develop_or:			# array: (label, operation, $d, $s, $t)
	addi $t3, $t3, 2	# $s address
	sb $t1, ($t3)		# save register
	addi $t1, $t1, 1	# register++
	addi $t3, $t3, 1	# $t address
	sb $t1, ($t3)		# save register
	addi $t1, $t1, 1	# register++
	addi $t3, $t3, -2	# $d address
	sb $t1, ($t3)		# save register (no register++)
	addi $t3, $t3, -1	# return to iteration's operation
	j develop_data_end
	
develop_ori:			# array: (label, operation, $t, $s, imm)
	addi $t3, $t3, 2	# $s address
	sb $t1, ($t3)		# save register
	addi $t1, $t1, 1	# register++
	addi $t3, $t3, 1	# imm address
	sb $t2, ($t3)		# save constant
	addi $t2, $t2, 1	# constant++
	addi $t3, $t3, -2	# $t address
	sb $t1, ($t3)		# save register (no register++)
	addi $t3, $t3, -1	# return to iteration's operation
	j develop_data_end
	
develop_lw: 			# array: (label, operation, $t, constant, $s)
	addi $t3, $t3, 2	# constant address
	sb $t2, ($t3)		# save constant
	addi $t2, $t2, 1	# constant++
	addi $t3, $t3, 1	# $s address
	sb $t1, ($t3)		# save register
	addi $t1, $t1, 1	# register++
	addi $t3, $t3, -2	# $t address
	sb $t1, ($t3)		# save register (no register++)
	addi $t3, $t3, -1	# return to iteration's operation
	j develop_data_end
	
develop_sw: 			# array: (label, operation, $t, constant, $s)
	addi $t3, $t3, 2	# constant address
	sb $t2, ($t3)		# save constant
	addi $t2, $t2, 1	# constant++
	addi $t3, $t3, 1	# $s address
	sb $t1, ($t3)		# save register
	addi $t1, $t1, 1	# register++
	addi $t3, $t3, -2	# $t address
	sb $t1, ($t3)		# save register (no register++)
	addi $t3, $t3, -1	# return to iteration's operation
	j develop_data_end

develop_j:			# array: (label, operation, targetLabel, , )
	addi $t3, $t3, 1	# targetLabel address
	sb $t0, ($t3)		# save label
	addi $t3, $t3, -1	# operation address (in case first opcode is j)
	beq $t0, 0, develop_data_end	# skip step if first opcode is j
	addi $t3, $t3, -6	# previous opcode label address
	sb $t0, ($t3)		# save label
	addi $t3, $t3, 6	# return to iteration's operation
	addi $t1, $t1, 1	# no destination/target register, register++
	j develop_data_end	

develop_beq:			# array: (label, operation, $s, $t, targetLabel)
	addi $t3, $t3, 3	# targetLabel address
	sb $t0, ($t3)		# save label
	addi $t3, $t3, -1	# $t address
	sb $t1, ($t3)		# save register
	addi $t1, $t1, 1	# register++
	addi $t3, $t3, -1	# $s address
	sb $t1, ($t3)		# save register (no register++)
	addi $t3, $t3, -1	# operation address (in case first opcode is j)
	beq $t0, 0, develop_data_end	# skip step if first opcode is j
	addi $t3, $t3, -6	# previous opcode label address
	sb $t0, ($t3)		# save label
	addi $t3, $t3, 6	# return to iteration's operation
	j develop_data_end	

develop_bne:			# array: (label, operation, $s, $t, targetLabel)
	addi $t3, $t3, 3	# targetLabel address
	sb $t0, ($t3)		# save label
	addi $t3, $t3, -1	# $t address
	sb $t1, ($t3)		# save register
	addi $t1, $t1, 1	# register++
	addi $t3, $t3, -1	# $s address
	sb $t1, ($t3)		# save register (no register++)
	addi $t3, $t3, -1	# operation address (in case first opcode is j)
	beq $t0, 0, develop_data_end	# skip step if first opcode is j
	addi $t3, $t3, -6	# previous opcode label address
	sb $t0, ($t3)		# save label
	addi $t3, $t3, 6	# return to iteration's operation
	j develop_data_end	

develop_data_end:		# run after EACH develop_data iteration
	addi $t0, $t0, 1	# iteration++
	addi $t3, $t3, 5	# next array row
	beq $t0, 5, return	# if 5th iteration done, quit loop
	j develop_data_loop
	
print_assembly_code:		# 1=add, 2=addi, 3=or, 4=ori, 5=lw, 6=sw, 7=j, 8=beq, 9=bne
	li $t0, 0		# iteration
	la $t1, array		# address of array (start at label)
	# $t2 = byte value of $t1
	li $t3, 6		# label/operation index factor
	li $t4, 4		# register/constants index factor
print_assembly_code_loop:
	lb $t2, ($t1)		# label value
	beq $t2, $zero, print_tab	# if label doesn't exist, skip print_label
print_label:
	mult  $t2, $t3		# offset calculation
	mflo $t2		# get offset
	la $a0, labels($t2)	# specified labels address 
	li $v0, 4
	syscall			# PRINT LABEL
print_colon:
	la $a0, colon
	li $v0, 4
	syscall			# PRINT COLON
print_tab:
	la $a0, tab
	li $v0, 4
	syscall			# PRINT TAB
print_operation:
	addi $t1, $t1, 1	# operation address
	lb $t2, ($t1)		# iteration operation value
	addi $t2, $t2, -1	# translate input to index
	mult  $t2, $t3		# offset calculation
	mflo $t2		# get offset
	la $a0, operations($t2)	# specified operations address
	li $v0, 4
	syscall			# PRINT OPERATION

	lb $t2, ($t1)		# iteration operation value for testing next print method
	addi $t1, $t1, 1	# next variable address (after operation byte)
	beq $t2, 1, print_rrr
	beq $t2, 2, print_rri
	beq $t2, 3, print_rrr
	beq $t2, 4, print_rri
	beq $t2, 5, print_rir
	beq $t2, 6, print_rir
	beq $t2, 7, print_l
	beq $t2, 8, print_rrl
	beq $t2, 9, print_rrl
	j error

print_rrr:
	lb $t2, ($t1)		# Rrr value
	mult  $t2, $t4		# offset calculation
	mflo $t2		# get offset
	la $a0, registers($t2)	# specified operations address
	li $v0, 4
	syscall			# PRINT Rrr
	la $a0, comma
	li $v0, 4
	syscall			# PRINT COMMA
	addi $t1, $t1, 1	# rRr address
	lb $t2, ($t1)		# rRr value
	mult  $t2, $t4		# offset calculation
	mflo $t2		# get offset
	la $a0, registers($t2)	# specified operations address
	li $v0, 4
	syscall			# PRINT rRr
	la $a0, comma
	li $v0, 4
	syscall			# PRINT COMMA
	addi $t1, $t1, 1	# rrR address
	lb $t2, ($t1)		# rrR value
	mult  $t2, $t4		# offset calculation
	mflo $t2		# get offset
	la $a0, registers($t2)	# specified operations address
	li $v0, 4
	syscall			# PRINT rrR
	j print_assembly_code_end
print_rri:
	lb $t2, ($t1)		# Rri value
	mult  $t2, $t4		# offset calculation
	mflo $t2		# get offset
	la $a0, registers($t2)	# specified operations address
	li $v0, 4
	syscall			# PRINT Rri
	la $a0, comma
	li $v0, 4
	syscall			# PRINT COMMA
	addi $t1, $t1, 1	# rRi address
	lb $t2, ($t1)		# rRi value
	mult  $t2, $t4		# offset calculation
	mflo $t2		# get offset
	la $a0, registers($t2)	# specified operations address
	li $v0, 4
	syscall			# PRINT rRi
	la $a0, comma
	li $v0, 4
	syscall			# PRINT COMMA
	addi $t1, $t1, 1	# rrI address
	lb $t2, ($t1)		# rrI value
	mult  $t2, $t4		# offset calculation
	mflo $t2		# get offset
	la $a0, constants($t2)	# specified operations address
	li $v0, 4
	syscall			# PRINT rrI
	j print_assembly_code_end
print_rir:
	lb $t2, ($t1)		# Rir value
	mult  $t2, $t4		# offset calculation
	mflo $t2		# get offset
	la $a0, registers($t2)	# specified operations address
	li $v0, 4
	syscall			# PRINT Rir
	la $a0, comma
	li $v0, 4
	syscall			# PRINT COMMA
	addi $t1, $t1, 1	# rIr address
	lb $t2, ($t1)		# rIr value
	mult  $t2, $t4		# offset calculation
	mflo $t2		# get offset
	la $a0, constants($t2)	# specified operations address
	li $v0, 4
	syscall			# PRINT rIr
	la $a0, open_p
	li $v0, 4
	syscall			# PRINT (
	addi $t1, $t1, 1	# riR address
	lb $t2, ($t1)		# riR value
	mult  $t2, $t4		# offset calculation
	mflo $t2		# get offset
	la $a0, registers($t2)	# specified operations address
	li $v0, 4
	syscall			# PRINT riR
	la $a0, close_p
	li $v0, 4
	syscall			# PRINT )
	j print_assembly_code_end
print_rrl:
	lb $t2, ($t1)		# Rrl value
	mult  $t2, $t4		# offset calculation
	mflo $t2		# get offset
	la $a0, registers($t2)	# specified operations address
	li $v0, 4
	syscall			# PRINT Rrl
	la $a0, comma
	li $v0, 4
	syscall			# PRINT COMMA
	addi $t1, $t1, 1	# rRl address
	lb $t2, ($t1)		# rRl value
	mult  $t2, $t4		# offset calculation
	mflo $t2		# get offset
	la $a0, registers($t2)	# specified operations address
	li $v0, 4
	syscall			# PRINT rRl
	la $a0, comma
	li $v0, 4
	syscall			# PRINT COMMA
	addi $t1, $t1, 1	# rrL address
	lb $t2, ($t1)		# rrL value
	mult  $t2, $t3		# offset calculation
	mflo $t2		# get offset
	la $a0, labels($t2)	# specified operations address
	li $v0, 4
	syscall			# PRINT rrL
	j print_assembly_code_end
print_l:
	lb $t2, ($t1)		# L value
	mult  $t2, $t3		# offset calculation
	mflo $t2		# get offset
	la $a0, labels($t2)	# specified operations address
	li $v0, 4
	syscall			# PRINT L
	addi $t1, $t1, 2	# move address to last (empty) item
	j print_assembly_code_end

print_assembly_code_end:
	la $a0, new_line
	li $v0, 4
	syscall			# PRINT NEW LINE
	addi $t1, $t1, 1	# iterate array row
	addi $t0, $t0, 1	# iteration++
	beq $t0, 5, return	# if 5th iteration done, quit loop
	j print_assembly_code_loop
	
print_machine_code:
	li $t0, 0		# iteration
	la $t1, array		# address of array starting at iteration operation
	addi $t1, $t1, 1
	# $t2 = value at $t1
	# $t3 = machine code in decimal
print_machine_code_loop:
	lb $t2, ($t1)		# operation value
	beq $t2, 1, calculate_add
	beq $t2, 2, calculate_addi
	beq $t2, 3, calculate_or
	beq $t2, 4, calculate_ori
	beq $t2, 5, calculate_lw
	beq $t2, 6, calculate_sw
	beq $t2, 7, calculate_j
	beq $t2, 8, calculate_beq
	beq $t2, 9, calculate_bne
	lb $a0, ($t1)
	li $v0, 1
	syscall
	j error

calculate_add:
	li $t3, 32		# value of add operation
	addi $t1, $t1, 1	# Rrr address
	lb $t2, ($t1)		# register index
	ble $t2, 7, skip1	# if not $t8/$t9, add only 8
	addi $t2, $t2, 8	# add 16 if register $t8/$t9
skip1:	addi $t2, $t2, 8
	li $t4, 2048		# $d * 2^11
	mult $t2, $t4
	mflo $t2
	add $t3, $t3, $t2	# op. + $d
	addi $t1, $t1, 1	# rRr address
	lb $t2, ($t1)		# register index
	ble $t2, 7, skip2	# if not $t8/$t9, add only 8
	addi $t2, $t2, 8	# add 16 if register $t8/$t9
skip2:	addi $t2, $t2, 8
	li $t4, 2097152		# $s * 2^21
	mult $t2, $t4
	mflo $t2
	add $t3, $t3, $t2	# op. + $d + $s
	addi $t1, $t1, 1	# rrR address
	lb $t2, ($t1)		# register index
	ble $t2, 7, skip3	# if not $t8/$t9, add only 8
	addi $t2, $t2, 8	# add 16 if register $t8/$t9
skip3:	addi $t2, $t2, 8
	li $t4, 65536		# $s * 2^16
	mult $t2, $t4
	mflo $t2
	add $t3, $t3, $t2	# op. + $d + $s + $t
	addi $t1, $t1, 2	# next iteration op
	j calculate_end

calculate_addi:
	li $t3, 536870912	# value of addi operation
	addi $t1, $t1, 1	# Rri address
	lb $t2, ($t1)		# register index
	ble $t2, 7, skip4	# if not $t8/$t9, add only 8
	addi $t2, $t2, 8	# add 16 if register $t8/$t9
skip4:	addi $t2, $t2, 8
	li $t4, 65536		# $t * 2^16
	mult $t2, $t4
	mflo $t2
	add $t3, $t3, $t2	# op. + $d
	addi $t1, $t1, 1	# rRi address
	lb $t2, ($t1)		# register index
	ble $t2, 7, skip5	# if not $t8/$t9, add only 8
	addi $t2, $t2, 8	# add 16 if register $t8/$t9
skip5:	addi $t2, $t2, 8
	li $t4, 2097152		# $s * 2^21
	mult $t2, $t4
	mflo $t2
	add $t3, $t3, $t2	# op. + $d + $s
	addi $t1, $t1, 1	# rrR address
	lb $t2, ($t1)		# constant - 100
	addi $t2, $t2, 100	# constant
	add $t3, $t3, $t2	# op. + $t + $s + constant
	addi $t1, $t1, 2	# next iteration op
	j calculate_end

calculate_or:
	li $t3, 37		# value of or operation
	addi $t1, $t1, 1	# Rrr address
	lb $t2, ($t1)		# register index
	ble $t2, 7, skip6	# if not $t8/$t9, add only 8
	addi $t2, $t2, 8	# add 16 if register $t8/$t9
skip6:	addi $t2, $t2, 8
	li $t4, 2048		# $d * 2^11
	mult $t2, $t4
	mflo $t2
	add $t3, $t3, $t2	# op. + $d
	addi $t1, $t1, 1	# rRr address
	lb $t2, ($t1)		# register index
	ble $t2, 7, skip7	# if not $t8/$t9, add only 8
	addi $t2, $t2, 8	# add 16 if register $t8/$t9
skip7:	addi $t2, $t2, 8
	li $t4, 2097152		# $s * 2^21
	mult $t2, $t4
	mflo $t2
	add $t3, $t3, $t2	# op. + $d + $s
	addi $t1, $t1, 1	# rrR address
	lb $t2, ($t1)		# register index
	ble $t2, 7, skip8	# if not $t8/$t9, add only 8
	addi $t2, $t2, 8	# add 16 if register $t8/$t9
skip8:	addi $t2, $t2, 8
	li $t4, 65536		# $s * 2^16
	mult $t2, $t4
	mflo $t2
	add $t3, $t3, $t2	# op. + $d + $s + $t
	addi $t1, $t1, 2	# next iteration op
	j calculate_end

calculate_ori:
	li $t3, 872415232	# value of ori operation
	addi $t1, $t1, 1	# Rri address
	lb $t2, ($t1)		# register index
	ble $t2, 7, skip9	# if not $t8/$t9, add only 8
	addi $t2, $t2, 8	# add 16 if register $t8/$t9
skip9:	addi $t2, $t2, 8
	li $t4, 65536		# $t * 2^16
	mult $t2, $t4
	mflo $t2
	add $t3, $t3, $t2	# op. + $d
	addi $t1, $t1, 1	# rRi address
	lb $t2, ($t1)		# register index
	ble $t2, 7, skip10	# if not $t8/$t9, add only 8
	addi $t2, $t2, 8	# add 16 if register $t8/$t9
skip10:	addi $t2, $t2, 8
	li $t4, 2097152		# $s * 2^21
	mult $t2, $t4
	mflo $t2
	add $t3, $t3, $t2	# op. + $d + $s
	addi $t1, $t1, 1	# rrR address
	lb $t2, ($t1)		# constant - 100
	addi $t2, $t2, 100	# constant
	add $t3, $t3, $t2	# op. + $t + $s + constant
	addi $t1, $t1, 2	# next iteration op
	j calculate_end

calculate_lw:
	li $t3, 2348810240	# value of lw operation
	addi $t1, $t1, 1	# Rir address
	lb $t2, ($t1)		# register index
	ble $t2, 7, skip11	# if not $t8/$t9, add only 8
	addi $t2, $t2, 8	# add 16 if register $t8/$t9
skip11:	addi $t2, $t2, 8
	li $t4, 65536		# $t * 2^16
	mult $t2, $t4
	mflo $t2
	add $t3, $t3, $t2	# op. + $d
	addi $t1, $t1, 2	# riR address
	lb $t2, ($t1)		# register index
	ble $t2, 7, skip12	# if not $t8/$t9, add only 8
	addi $t2, $t2, 8	# add 16 if register $t8/$t9
skip12:	addi $t2, $t2, 8
	li $t4, 2097152		# $s * 2^21
	mult $t2, $t4
	mflo $t2
	add $t3, $t3, $t2	# op. + $d + $s
	addi $t1, $t1, -1	# rIr address
	lb $t2, ($t1)		# constant - 100
	addi $t2, $t2, 100	# constant
	add $t3, $t3, $t2	# op. + $t + $s + constant
	addi $t1, $t1, 3	# next iteration op
	j calculate_end

calculate_sw:
	li $t3, 2885681152	# value of sw operation
	addi $t1, $t1, 1	# Rir address
	lb $t2, ($t1)		# register index
	ble $t2, 7, skip13	# if not $t8/$t9, add only 8
	addi $t2, $t2, 8	# add 16 if register $t8/$t9
skip13:	addi $t2, $t2, 8
	li $t4, 65536		# $t * 2^16
	mult $t2, $t4
	mflo $t2
	add $t3, $t3, $t2	# op. + $d
	addi $t1, $t1, 2	# riR address
	lb $t2, ($t1)		# register index
	ble $t2, 7, skip14	# if not $t8/$t9, add only 8
	addi $t2, $t2, 8	# add 16 if register $t8/$t9
skip14:	addi $t2, $t2, 8
	li $t4, 2097152		# $s * 2^21
	mult $t2, $t4
	mflo $t2
	add $t3, $t3, $t2	# op. + $d + $s
	addi $t1, $t1, -1	# rIr address
	lb $t2, ($t1)		# constant - 100
	addi $t2, $t2, 100	# constant
	add $t3, $t3, $t2	# op. + $t + $s + constant
	addi $t1, $t1, 3	# next iteration op
	j calculate_end

calculate_j:
	li $t3, 134217728	# value of j operation
	addi $t1, $t1, 1	# imm address
	lb $t2, ($t1)		# imm index
	add $t3, $t3, $t2	# op. + address
	addi $t3, $t3, 1048578	# base address
	addi $t1, $t1, 4	# next iteration op
	j calculate_end
	
calculate_beq:
	li $t3, 268435456	# value of beq operation
	addi $t1, $t1, 1	# Rri address
	lb $t2, ($t1)		# register index
	ble $t2, 7, skip15	# if not $t8/$t9, add only 8
	addi $t2, $t2, 8	# add 16 if register $t8/$t9
skip15:	addi $t2, $t2, 8
	li $t4, 2097152		# $t * 2^21
	mult $t2, $t4
	mflo $t2
	add $t3, $t3, $t2	# op. + $d
	addi $t1, $t1, 1	# rRi address
	lb $t2, ($t1)		# register index
	ble $t2, 7, skip16	# if not $t8/$t9, add only 8
	addi $t2, $t2, 8	# add 16 if register $t8/$t9
skip16:	addi $t2, $t2, 8
	li $t4, 65536		# $s * 2^16
	mult $t2, $t4
	mflo $t2
	add $t3, $t3, $t2	# op. + $d + $s
	addi $t1, $t1, 1	# rrR address
	lb $t2, ($t1)		# constant
	addi $t2, $t2, 1048578	# constant + base address
	add $t3, $t3, $t2	# op. + $t + $s + constant
	addi $t1, $t1, 2	# next iteration op
	j calculate_end

calculate_bne:
	li $t3, 335544320	# value of bne operation
	addi $t1, $t1, 1	# Rri address
	lb $t2, ($t1)		# register index
	ble $t2, 7, skip17	# if not $t8/$t9, add only 8
	addi $t2, $t2, 8	# add 16 if register $t8/$t9
skip17:	addi $t2, $t2, 8
	li $t4, 2097152		# $t * 2^21
	mult $t2, $t4
	mflo $t2
	add $t3, $t3, $t2	# op. + $d
	addi $t1, $t1, 1	# rRi address
	lb $t2, ($t1)		# register index
	ble $t2, 7, skip18	# if not $t8/$t9, add only 8
	addi $t2, $t2, 8	# add 16 if register $t8/$t9
skip18:	addi $t2, $t2, 8
	li $t4, 65536		# $s * 2^16
	mult $t2, $t4
	mflo $t2
	add $t3, $t3, $t2	# op. + $d + $s
	addi $t1, $t1, 1	# rrR address
	lb $t2, ($t1)		# constant
	addi $t2, $t2, 1048578	# constant + base address
	add $t3, $t3, $t2	# op. + $t + $s + constant
	addi $t1, $t1, 2	# next iteration op
	j calculate_end

calculate_end:
	move $a0, $t3
	li $v0, 34
	syscall			# print calculated machine code in hex
	la $a0, new_line
	li $v0, 4
	syscall			# print new line
	addi $t0, $t0, 1	# iteration++
	beq $t0, 5, return	# if 5th iteration done, quit loop
	j print_machine_code_loop

	
	
return:
	jr $ra

error:
	la $a0, error_message
	li $v0, 4
	syscall 		# print error message

terminate: