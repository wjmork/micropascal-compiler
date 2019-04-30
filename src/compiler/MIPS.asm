.data
dollars:	.word	0
yen:	.word	0
bitcoins:	.word	0
.text
main:
addi	$sp,	$sp,	-40
sw	$s7,	36($sp)
sw	$s6,	32($sp)
sw	$s5,	28($sp)
sw	$s4,	24($sp)
sw	$s3,	20($sp)
sw	$s2,	16($sp)
sw	$s1,	12($sp)
sw	$s0,	8($sp)
sw	$fp,	4($sp)
sw	$ra,	0($sp)
addi   $s0,   $zero, 10000
sw	$s0,	dollars
la	$t0,	dollars
addi   $t1,   $zero, 110
mult   $t0,   $t1
mflo   $s0
sw	$s0,	yen
la	$t0,	dollars
addi   $t1,   $zero, 3900
div	$t0,	$t1
mflo	$s0
sw	$s0,	bitcoins
lw	$s7,	36($sp)
lw	$s6,	32($sp)
lw	$s5,	28($sp)
lw	$s4,	24($sp)
lw	$s3,	20($sp)
lw	$s2,	16($sp)
lw	$s1,	12($sp)
lw	$s0,	8($sp)
lw	$fp,	4($sp)
lw	$ra,	0($sp)
addi	$sp,	$sp,	40
jr	$ra
