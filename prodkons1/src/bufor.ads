-- Wersja synchronizacji oparta na typach chronionych
--

package Bufor is
   
   subtype T is Float; -- deklaracja typu danych bufora
   Rozmiar_max: constant Integer := 100;
   subtype Rozmiar is Integer range 1..Rozmiar_max;
   type Buff_Arr is array (Integer range <>) of T;
   
   protected type Bufor_Obj(N: Rozmiar := 10) is
      entry Pobierz(X: out T);
      entry Wstaw(X: in T);
   private
      Buf: Buff_Arr(0..N);
      Wej: Integer := 0;
      Wyj: Integer := 0;
      Licznik: Integer := 0;
   end Bufor_Obj;
   
   type Bufor_Obj_Acc is access all Bufor_Obj;
   
   buf_acc : Bufor_Obj_Acc;
   
end Bufor;
