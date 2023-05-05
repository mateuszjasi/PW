with Ada.Text_IO, Semafor_Bin, Zadania;
use Ada.Text_IO, Semafor_Bin, Zadania;

package body Bufor is
   
   task body Bufor_Obj is
      tab: Buff_Arr(0 .. N - 1);
      we, wy: Integer := 0;
      licznik: Integer := 0;
      koniec : Boolean := false;
   begin
      accept Start do
         sem_bin_acc.all.PB;
         Put("START task - Bufor");
         New_line;
         sem_bin_acc.all.VB;
      end Start;
      loop
         exit when koniec;
         select
            when licznik < N =>
               accept wstaw(x: in T) do
                  tab(we) := x;
               end wstaw;
               licznik := licznik + 1;
               we := (we + 1) mod N;
         or
            when licznik > 0 =>
               accept pobierz(x: out T) do
                  x := tab(wy);
               end pobierz;
               licznik := licznik - 1;
               wy := (wy + 1) mod N;
         or
            accept Stop do
               sem_bin_acc.all.PB;
               Put("STOP task - Bufor");
               New_line;
               sem_bin_acc.all.VB;
               koniec := true;
            end Stop;
         end select;
      end loop;
   end Bufor_Obj;
begin
   null;
end Bufor;
