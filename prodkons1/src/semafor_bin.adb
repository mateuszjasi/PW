package body Semafor_Bin is
   
   protected body Semafor_Bin is
      entry PB when count > 0 is
      begin
         count := count -1;
      end PB;
      
      procedure VB is
      begin
         count := count + 1;
      end VB;
   end Semafor_Bin;
   
begin
   null;
end Semafor_Bin;
