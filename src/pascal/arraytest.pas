program arraytest;
var
    foo : array[0:2] of integer;
	bar : integer;
    index: integer;
begin
    index := 0;
    while
    index < 3
    do
    begin
        foo[index] = (index);
        index := index + 1
    end;
end;
begin
    foo[0] := 1;
    foo[1] := 2;
    foo[2] := 3;
	bar := foo[0] + foo[1] + foo[2]
end.