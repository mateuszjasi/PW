-- Wersja synchronizacji oparta na spotkaniach
--

package Bufor is
   
   subtype T is Float; -- deklaracja typu danych bufora
   Rozmiar_max: constant Integer := 100;
   subtype Rozmiar is Integer range 1..Rozmiar_max;
   type Buff_Arr is array (Integer range <>) of T;
   
   task type Bufor_Obj(N: Integer) is
      entry wstaw(x: in T);
      entry pobierz(x: out T);
      entry start;
      entry stop;
   end Bufor_Obj;
   
   type Bufor_Obj_Acc is access all Bufor_Obj;
   
   buf_acc : Bufor_Obj_Acc;
   
end Bufor;
