with Bufor, Semafor_Bin;
use Bufor, Semafor_Bin;

package Zadania is
   task type Producent(nr : Integer) is
      entry Start;
      entry Stop;
   end Producent;
   
   task type Konsument(nr : Integer) is
      entry Start;
      entry Stop;
   end Konsument;
   
   sem_bin_acc : Semafor_Bin_Acc;
   
   licznik_prod : Integer;
   licznik_kons : Integer;
end Zadania;
