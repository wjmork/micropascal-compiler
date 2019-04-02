package syntaxtree;

public class SubProgramNode extends SyntaxTreeNode {

    public String indentedToString(int level) {
        String answer = this.indentation(level);
        return answer;
    }
}
