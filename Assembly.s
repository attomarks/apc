    .file   "test.c"
    .section    .rodata
.LC0:
    .string "c=%d\n"
    .text
    .globl  foo
    .type   foo, @function
foo:
    pushq   %rbp
    movq    %rsp, %rbp
    subq    $8, %rsp
    movl    %edi, -4(%rbp)
    movl    %esi, -8(%rbp)
    movl    %eax, %ebx
    movl    -8(%rbp), %ecx
    movl    -4(%rbp), %eax
    addl    %ecx, %eax
    leave
    ret
    .size   foo,.-foo
    .globl  main
    .type   main, @function
main:
    pushq   %rbp
    movq    %rsp, %rbp
    subq    $16, %rsp
    movl    $1, -4(%rbp)
    movl    $2, -8(%rbp)
    movl    -4(%rbp), %edi
    movl    -8(%rbp), %esi
    call    foo
    movl    %eax, -12(%rbp)
    movl    $0, -16(%rbp)
.L0:
    movl    %eax, %ebx
    movl    $5, %ecx
    movl    -16(%rbp), %eax
    cmpl    %ecx, %eax
    setl    %al
    movzbl  %al, %eax
    testl    %eax, %eax
    jnz .L1
    jmp .L2
.L1:
    movl    %eax, %ebx
    movl    -16(%rbp), %ecx
    movl    -12(%rbp), %eax
    addl    %ecx, %eax
    movl    %eax, -12(%rbp)
    movl    %eax, %ebx
    movl    $1, %ecx
    movl    -16(%rbp), %eax
    addl    %ecx, %eax
    movl    %eax, -16(%rbp)
    jmp .L0
.L2:
    movl    -12(%rbp), %esi
    leaq    .LC0(%rip), %rdi
    movl    $0, %eax
    call    printf@PLT
    movl    $0, %eax
    leave
    ret
    .size   main,.-main
