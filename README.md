### TrixVM Syntax

Stack Manipulation
push <integer>: push 5
pushr <register>: push r0
pop: pop
peek: peek
break: break
dup: dup;

Arithmetic
ADD
SUB
MUL
DIV

Register Load
li <register> <integer>: li r0 5
lw <register> <heap_address>: lw r0 0x000
lwr <register1> <register2>: Registered version of lw
ls <register>: ls r0, loads top of stack to register

Memory Store [heap]
sw <register> <heap_addr>: sw r0 0x002
ss <heap_addr>: ss 0x4232, stores top of stack to addr
ssr <register>: ssr r1, stores top of stack to an addr in register





