program example;
var
    foo : array[0:2] of integer;
var
	  bar : integer;
begin
    foo[0] := 1;
    foo[1] := 2;
    foo[2] := 3;
	  bar := foo[0] + foo[1] + foo[2]
end.