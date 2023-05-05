package body Bufor is
   
   protected body Bufor_Obj is
      entry Pobierz(X: out T) when Licznik > 0 is
      begin
         X := Buf(Wyj);
         Wyj := (Wyj + 1) mod N;
         Licznik := Licznik - 1;
      end Pobierz;
      
      entry Wstaw(X: in T) when Licznik < N is
      begin
         Buf(Wej) := X;
         Wej := (Wej + 1) mod N;
         Licznik := Licznik + 1;
      end Wstaw;
   end Bufor_Obj;
   
begin
   null;
end Bufor;
