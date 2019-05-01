{ money.pas }
{ An example program for a syntax tree.}
{ Exchange rates as of 21 February 2019.}

program sample;
var dollars, yen, bitcoins: integer;

begin
  dollars := 10000;
  yen := dollars * 110;
  bitcoins := dollars / 3900
end
.