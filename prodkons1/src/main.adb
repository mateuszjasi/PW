with Ada.Command_Line,Ada.Text_IO, Ada.Integer_Text_IO, Ada.Float_Text_IO,
Ada.Exceptions, Zadania, ada.Unchecked_Deallocation, Bufor, Semafor_Bin;
use Ada.Command_Line, Ada.Text_IO, Ada.Integer_Text_IO, Ada.Float_Text_IO,
Ada.Exceptions, Zadania, Bufor, Semafor_Bin;
-- Wersja synchronizacji oparta na typach chronionych
--
procedure Main is

   procedure Free is new Ada.Unchecked_Deallocation(Bufor_Obj, Bufor_Obj_Acc);
   procedure Free is new Ada.Unchecked_Deallocation
     (Semafor_Bin.Semafor_Bin, Semafor_Bin.Semafor_Bin_Acc);

   prod1 : Producent(0);
   prod2 : Producent(1);
   kons1 : Konsument(0);
   kons2 : Konsument(1);
   kons3 : Konsument(2);
begin
   licznik_prod := 60;
   licznik_kons := 40;
   buf_acc := new Bufor_Obj(5);
   sem_bin_acc := new Semafor_Bin.Semafor_Bin(1);
   prod1.Start;
   prod2.Start;
   kons1.Start;
   kons2.Start;
   kons3.Start;
   prod1.Stop;
   prod2.Stop;
   kons1.Stop;
   kons2.Stop;
   kons3.Stop;
   Free(buf_acc);
   Free(sem_bin_acc);
   Put(" Koniec programu");
   New_line;
end Main;
