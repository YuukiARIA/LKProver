package lovelogic.latex;

import lovelogic.formatter.LaTeXFormulaFormatter;
import lovelogic.prover.ProofStep;
import lovelogic.sequent.Sequent;
import lovelogic.syntax.Formula;
import util.MTree;
import util.StringUtils;
import util.strconv.IStringConverter;

public class LaTeXStringBuilder
{
	private static final IStringConverter<Formula> converter = new IStringConverter<Formula>()
	{
		public String toString(Formula x)
		{
			return LaTeXFormulaFormatter.getInstance().toString(x);
		}
	};

	private LaTeXStringBuilder() { }

	public static String toLaTeX(MTree<ProofStep> prtree)
	{
		StringBuilder buf = new StringBuilder();
		toLaTeX(buf, prtree, 0);
		return buf.toString();
	}

	private static void toLaTeX(StringBuilder buf, MTree<ProofStep> prtree, int depth)
	{
		String indent = StringUtils.repeat(depth, " ");

		if (prtree.hasNoSubtree())
		{
			buf.append(indent);
			toString(buf, prtree.get().getSequent());
			buf.append('\n');
			return;
		}

		ProofStep pr = prtree.get();
		buf.append(indent + "\\infer");
		if (!pr.getDeductionName().isEmpty())
		{
			buf.append("[\\hbox{" + pr.getDeductionName() + "}]");
		}
		buf.append("{");
		toString(buf, pr.getSequent());
		buf.append("}\n");

		buf.append(indent + "{\n");
		boolean first = true;
		for (MTree<ProofStep> subtree : prtree.getSubtrees())
		{
			if (!first)
			{
				buf.append(indent + "&\n");
			}
			first = false;
			toLaTeX(buf, subtree, depth + 1);
		}
		buf.append(indent + "}\n");
	}

	private static void toString(StringBuilder buf, Sequent sequent)
	{
		buf.append("\\strut");
		buf.append(StringUtils.join(sequent.getLeftFormulae(), ",\\ ", converter));
		buf.append("\\ \\vdash \\ ");
		buf.append(StringUtils.join(sequent.getRightFormulae(), ",\\ ", converter));
	}
}
