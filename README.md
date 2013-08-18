LKProver
========

Description
-----------
This is an LK proof searcher and visualizer.

Implimented Deduction Rules
---------------------------
Formulae sequences in each sequents are regarded as set.
Therefore rules of contraction and permutation are not needed.

    ──────── (Axiom)
     L, A ├ A, R
    
     L ├ A, R   L, B ├ R
    ──────────── (Imp-L)
        L, A ⇒ B ├ R
    
      L, A ├ B, R
    ───────── (Imp-R)
     L ├ A ⇒ B, R
    
     L, A ├ R   L, B ├ R
    ──────────── (Or-L)
        L, A ∨ B ├ R
    
        L ├ A, R
    ───────── (Or-R1)
     L ├ A ∨ B, R
    
       L ├ B, R
    ───────── (Or-R2)
     L ├ A ∨ B, R
    
      L ├ A, B, R
    ───────── (Or-R)
     L ├ A ∨ B, R
    
        L, A ├ R
    ───────── (And-L1)
     L, A ∧ B ├ R
    
      L, B ├ R
    ──────── (And-L2)
    L, A ∧ B ├ R
    
      L, A, B ├ R
    ───────── (And-L)
     L, A ∧ B ├ R
    
     L ├ A, R  L ├ B, R
    ──────────── (And-R)
        L ├ A ∧ B, R
    
      L ├ A, R
    ─────── (Not-L)
     L, ¬A ├ R
    
      L, A ├ R
    ─────── (Not-R)
     L ├ ¬A, R
