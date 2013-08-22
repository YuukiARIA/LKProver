LKProver
========

Description
-----------

This is an LK proof searcher and visualizer.


Syntax
------

### Syntax of a propositional formula

Only implication is right-associative.

    <<Equiv>>
        ::= <<Impl>> { "<=>" <<Impl>> }
    
    <<Impl>>
        ::= <<Disj>> [ "=>" <<Impl>> ]
    
    <<Disj>>
        ::= <<Conj>> { "\/" <<Conj>> }
    
    <<Conj>>
        ::= <<Neg>> { "/\" <<Neg>> }
    
    <<Neg>>
        ::= "~" <<Neg>>
          | <<Primary>>
    
    <<Primary>>
        ::= <LITERAL>
          | "(" <<Equiv>> ")"

### Literals

A sequence of more than one character of alphabet (A-Z, a-z) or digits (0-9)
is a literal (propositional variable).
The literals are case sensitive.


Implemented Deduction Rules
---------------------------

Formulae sequences in each sequents are regarded as set.
Therefore rules of contraction and permutation are not needed.

    -------------- (Axiom)
     L, A |- A, R
    
     L |- A, R    L, B |- R
    ------------------------ (Imp-L)
         L, A => B |- R
    
      L, A |- B, R
    ---------------- (Imp-R)
     L |- A => B, R
    
     L, A |- R    L, B |- R
    ------------------------ (Or-L)
         L, A \/ B |- R
    
        L |- A, R
    ---------------- (Or-R1)
     L |- A \/ B, R
    
       L |- B, R
    ---------------- (Or-R2)
     L |- A \/ B, R
    
      L |- A, B, R
    ---------------- (Or-R)
     L |- A \/ B, R
    
        L, A |- R
    ---------------- (And-L1)
     L, A /\ B |- R
    
      L, B |- R
    ---------------- (And-L2)
     L, A /\ B |- R
    
      L, A, B |- R
    ---------------- (And-L)
     L, A /\ B |- R
    
     L |- A, R   L |- B, R
    ----------------------- (And-R)
        L |- A /\ B, R
    
     L |- A, R
    ------------ (Not-L)
     L, ~A |- R
    
      L, A |- R
    ------------ (Not-R)
     L |- ~A, R

Author
------

YuukiARIA
