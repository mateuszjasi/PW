with Ada.Numerics.Float_Random, Ada.Calendar, Ada.Real_Time,Ada.Text_IO,
     Ada.Integer_Text_IO, Ada.Float_Text_IO, Bufor, Semafor_Bin;
use Ada.Numerics.Float_Random, Ada.Calendar, Ada.Real_Time,Ada.Text_IO,
    Ada.Integer_Text_IO, Ada.Float_Text_IO, Bufor, Semafor_Bin;

package body Zadania is
   
   gen: Generator;
   first: Boolean := true;
   
procedure Produkuj(f: out T) is
      sc: Seconds_Count;
      ts: Time_Span;
begin
      if (first) then
         first := false;
         Split (Clock, sc, ts);
         Reset(gen, Integer(sc) + 1000);
      end if;
      f := Random(gen) * 10.0;
      delay(Duration(0.1 * f));
   end;
   
procedure Konsumuj(f: in T) is
      sc: Seconds_Count;
      ts: Time_Span;
begin
      if (first) then
         first := false;
         Split (Clock, sc, ts);
         Reset(gen, Integer(sc) + 1000);
      end if;
      delay(Duration(0.05 * f));
   end;
   
task body Producent is
      f : float;
begin
      accept Start do
         sem_bin_acc.all.PB;
         Put("[P-");
         Put(nr);
         Put("] :: Start - Produkcja");
         New_line;
         sem_bin_acc.all.VB;
      end Start;
      for i in Integer range 1..licznik_prod loop
         sem_bin_acc.all.PB;
         Put("[P-");
         Put(nr);
         Put("] :: Poczatek - Produkcja nr: ");
         Put(i);
         New_line;
         sem_bin_acc.all.VB;
         Produkuj(f);
         buf_acc.all.Wstaw(f);
         sem_bin_acc.all.PB;
         Put("[P-");
         Put(nr);
         Put("] :: Koniec - Produkcja nr: ");
         Put(i);
         Put(" - val= ");
         Put(f);
         New_line;
         sem_bin_acc.all.VB;
      end loop;
      accept Stop do
         sem_bin_acc.all.PB;
         Put("[P-");
         Put(nr);
         Put("] :: !! Produkcja zakonczona");
         New_line;
         sem_bin_acc.all.VB;
      end Stop;
   end;
   
task body Konsument is
      f : float;
begin
      accept Start do
         sem_bin_acc.all.PB;
         Put("[K-");
         Put(nr);
         Put("] :: Start - Knsumpcja");
         New_line;
         sem_bin_acc.all.VB;
      end Start;
      for i in Integer range 1..licznik_kons loop
         sem_bin_acc.all.PB;
         Put("[K-");
         Put(nr);
         Put("] :: Poczatek - Konsumpcja nr: ");
         Put(i);
         New_line;
         sem_bin_acc.all.VB;
         buf_acc.all.Pobierz(f);
         Konsumuj(f);
         sem_bin_acc.all.PB;
         Put("[K-");
         Put(nr);
         Put("] :: Koniec - Konsumpcja nr: ");
         Put(i);
         Put(" - val= ");
         Put(f);
         New_line;
         sem_bin_acc.all.VB;
      end loop;
      accept Stop do
         sem_bin_acc.all.PB;
         Put("[K-");
         Put(nr);
         Put("] :: !! Konsumpcja zakonczona");
         New_line;
         sem_bin_acc.all.VB;
      end Stop;
   end;
begin
   null;
end Zadania;
