.data
foo:	.word	0
bar:	.word	0

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
addi	$s0,	$zero,	1
sw	$s0,	foo
addi	$s0,	$zero,	2
sw	$s0,	foo
addi	$s0,	$zero,	3
sw	$s0,	foo
addi	$s3,	$zero,	0
li	$t0,	4
mult	$t0,	$s3
mflo	$s3
la	$s4,	foo
add	$s4,	$s3,	$s4
lw	$t1,	0($s4)
addi	$s4,	$zero,	1
li	$t0,	4
mult	$t0,	$s4
mflo	$s4
la	$s5,	foo
add	$s5,	$s4,	$s5
lw	$t2,	0($s5)
add	$t0,	$t1,	$t2
addi	$s3,	$zero,	2
li	$t0,	4
mult	$t0,	$s3
mflo	$s3
la	$s4,	foo
add	$s4,	$s3,	$s4
lw	$t1,	0($s4)
add	$s0,	$t0,	$t1
sw	$s0,	bar
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
