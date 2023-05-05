package Semafor_Bin is
   
   protected type Semafor_Bin(N: Natural := 1) is
      entry PB;
      procedure VB;
   private
      count: natural := N;
   end Semafor_Bin;
   type Semafor_Bin_Acc is access all Semafor_Bin;
   
end Semafor_Bin;
