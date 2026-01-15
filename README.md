# TrixBinary VM Documentation

## 1. Overview

TrixBinary is a stack-based virtual machine with 32-bit words and 4 general-purpose registers (`r0–r3`). It supports integer and floating-point arithmetic, conditional jumps, subroutine calls, and basic system I/O.

- **Memory:** `int[]` (32-bit words)
- **Registers:** `r0, r1, r2, r3`
- **Stack:** 32-bit words, used for arithmetic, function arguments, and return values
- **Program Counter (PC):** increments per instruction, points to memory index

The VM uses **segments** (`:name`) and **labels** (`LBL _name`) for structured code and control flow.

---

## 2. Registers

| Register | Purpose |
|----------|---------|
| `r0`     | Syscall number / general-purpose |
| `r1`     | General-purpose / argument |
| `r2`     | General-purpose / argument |
| `r3`     | General-purpose / temporary / callee-saved |

---

## 3. Instruction Format

- 32-bit instructions (1 word per instruction)
- Instructions with immediates use additional words
- Stack operations manipulate the 32-bit stack
- Arithmetic operations (`ADD`, `SUB`, `MUL`, `DIV`) pop operands and push results

---

## 4. Instruction Set

| Opcode    | Arguments | Stack Effect   | Description                                                    |
|-----------|-----------|----------------|----------------------------------------------------------------|
| `IPUSH`   | imm       | +1             | Push a 32-bit integer value onto the stack                     |
| `FPUSH`   | imm       | +1             | Push a 32-bit float value onto the stack                       |
| `PUSHR`   | register  | +1             | Push a 32-bit value from the specified register onto the stack |
| `POP`     | —         | -1             | Pop top of the stack                                           |
| `PEEK`    | —         | 0              | Print the top of the stack (debug)                             |
| `DUP`     | —         | +1             | Duplicate the top of the stack                                 |
| `I_ADD`   | —         | -1             | Pop two `integer` values, push sum                             |
| `I_SUB`   | —         | -1             | Pop two `integer` values, push difference                      |
| `I_MUL`   | —         | -1             | Pop two `integer` values, push product                         |
| `I_DIV`   | —         | -1             | Pop two `integer` values, push quotient                        | 
| `F_ADD`   | —         | -1             | Pop two `float` values, push sum                               |
| `F_SUB`   | —         | -1             | Pop two `float` values, push difference                        |
| `F_MUL`   | —         | -1             | Pop two `float` values, push product                           |
| `F_DIV`   | —         | -1             | Pop two `float` values, push quotient                          |
| `LI`      | reg, imm  | 0              | Load immediate into register                                   |
| `LS`      | reg       | -1             | Load top of stack into register                                |
| `LW`      | reg, addr | 0              | Load word value from heap into register                        |
| `JMP`     | label     | 0              | Unconditional jump                                             |
| `JZ`      | label     | -1             | Pop top; jump if zero                                          |
| `JNZ`     | label     | -1             | Pop top; jump if not zero                                      |
| `CALL`    | segment   | `CallStack`+1  | Push return address and jump to segment                        |
| `BREAK`   | —         | 0              | Stop execution or debug break                                  |
| `SYSCALL` | —         | varies         | Execute system call (see syscall table)                        |

---

## 5. Syscall Table

| ID  | Meaning      | Stack Effect |
|-----|--------------|--------------|
| 0   | Exit program | None         |
| 1   | Read int     | Push 1 int   |
| 2   | Print int    | Pop 1 int    |
| 3   | Read float   | Push 1 float |
| 4   | Print float  | Pop 1 float  |

**Example Usage:**

```asm
li r0 1       ; SYS_READ_INT
syscall           ; Waits for user to input an integer

li r0 2       ; SYS_PRINT_INT
syscall           ; Prints top of stack as an integer
```

## 6. Segments and Labels

### Segments start with :
```
:main
:fib
```

### Labels start with `LBL _name` and exist inside segments only
```
:main
    lbl _loop
    push 5
    jmp _loop
```

* CALL pushes the return address and jumps to a segment
* JMP changes the program counter to the instruction beneath the lbl
* BREAK stops execution and returns control (or halts)

## 7. Memory Model
* Memory is an array of 32-bit words: int[] memory
* Each instruction occupies 1 word
* Immediate values occupy the next word of the instruction
* Stack stores 32-bit values (ints or float raw bits)

|Memory|Description|
|------|-----------|
|Stack Memory| Responsible for handling the data manipulated for Arithmetic and IO|
|Call Stack| Responsible for keeping track of the segments being called and what part of the instruction to comeback to|
|Heap Memory| Is a general purpose memory for storing anything regardless of order|
|Program Memory| This is where the program is stored|

## 8. Context Window
Context Windows are the specific programs running in the Virtual Machine.
Each context windows has its own Memory Model isolated of other context windows.
Basically it is running its own environment unaware of parallel programs running along with it.

It has its own program counter and states.

### Context Window Switching

For the virtual machine to be able to run multiple context window, we need to keep track the 
program counter, and the values registers previously held. So that everytime we switch, we come back to where we were exactly
as we left it.

## 9. Example Programs
### 9.1 Read and print an integer
```
:main
    li r0 1        ; SYS_READ_INT
    syscall        ; blocks until user inputs an integer, pushes it onto stack

    li r0 2        ; SYS_PRINT_INT
    syscall        ; pops top of stack and prints it

    BREAK          ; stops execution

```

### 9.2 Read and print a float
```
:main
    li r0 3        ; SYS_READ_FLOAT
    syscall        ; blocks until user inputs a float, pushes raw bits onto stack

    li r0 4        ; SYS_PRINT_FLOAT
    syscall        ; pops top of stack and prints it as a float

    BREAK
```

### 9.3 Fibonacci using stack
```
; Fibonacci Sequence Test with Comments
:main

    ; Initialize first two numbers
    li r0 0          ; r0 = 0 (first Fibonacci number)
    li r1 1          ; r1 = 1 (second Fibonacci number)

    ; Push first number and print
    pushr r0
    peek             ; print r0

    ; Push second number and print
    pushr r1
    peek             ; print r1

    ; Calculate first sum and store in r2
    i_add            ; r0 + r1, push result
    peek             ; print result
    ls r2            ; store sum in r2

    ; Start of Fibonacci loop
    lbl fib
    ; Prepare r0 for next iteration
    pushr r1     ; push current number
    ls r0        ; r0 = top of stack

    ; Prepare r1 for next iteration
    pushr r2     ; push next number
    pushr r1     ; push next number again
    i_add        ; add
    peek         ; print
    ls r2        ; load to stack
    jmp fib      ; jump up to fib

    break
```

## 10. Notes & Conventions

* Stack values are always 32-bit words
* Registers are general-purpose
* PUSH is generic; type is tracked by the compiler
* SYSCALL interprets values using r0 (syscall ID)
* Jumps and calls use absolute instruction indices
* Use JMP / JZ / JNZ for loops
* Use CALL only for subroutines
