// Jigar Swaminarayan
package csp;

import java.time.LocalDate;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

/**
 * CSP: Calendar Satisfaction Problem Solver Provides a solution for scheduling
 * some n meetings in a given period of time and according to some unary and
 * binary constraints on the dates of each meeting.
 */
public class CSP {

	/**
	 * Public interface for the CSP solver in which the number of meetings, range of
	 * allowable dates for each meeting, and constraints on meeting times are
	 * specified.
	 * 
	 * @param nMeetings   The number of meetings that must be scheduled, indexed
	 *                    from 0 to n-1
	 * @param rangeStart  The start date (inclusive) of the domains of each of the n
	 *                    meeting-variables
	 * @param rangeEnd    The end date (inclusive) of the domains of each of the n
	 *                    meeting-variables
	 * @param constraints Date constraints on the meeting times (unary and binary
	 *                    for this assignment)
	 * @return A list of dates that satisfies each of the constraints for each of
	 *         the n meetings, indexed by the variable they satisfy, or null if no
	 *         solution exists.
	 */
	public static List<LocalDate> solve(int nMeetings, LocalDate rangeStart, LocalDate rangeEnd,
			Set<DateConstraint> constraints) {
		List<DateVar> vars = new ArrayList<DateVar>();
		List<LocalDate> emptyList = new ArrayList<LocalDate>();
		for (int i = 0; i < nMeetings; i++) {
			DateVar date = new DateVar(i);
			vars.add(date);
			emptyList.add(null);
		}
		for (DateConstraint dConstraint : constraints) {
			int arity = dConstraint.arity();
			if (arity == 1) {
				UnaryDateConstraint unaryConstraint = (UnaryDateConstraint) dConstraint;
				vars.get(unaryConstraint.L_VAL).unaryConstraint.add(unaryConstraint);
			} else if (arity == 2) {
				BinaryDateConstraint binaryConstraint = (BinaryDateConstraint) dConstraint;
				vars.get(binaryConstraint.R_VAL).binaryConstraint.add(binaryConstraint);
				vars.get(binaryConstraint.L_VAL).binaryConstraint.add(binaryConstraint);
			}
		}
		for (DateVar date : vars) {
			for (LocalDate lDate = rangeStart; lDate.compareTo(rangeEnd) <= 0; lDate = lDate.plusDays(1)) {
				date.domain.add(lDate);
			}
			date.domain = nodeConsistency(date);
		}
		return recursiveBacktracking(emptyList, vars);
	}

	public static List<LocalDate> recursiveBacktracking(List<LocalDate> assignment, List<DateVar> vars) {
		if (isComplete(assignment)) {
			return assignment;
		}
		int index = unassignedVal(assignment);
		DateVar unassignedVal = vars.get(index);
		for (LocalDate x : unassignedVal.domain) {
			assignment.set(unassignedVal.index, x);
			if (consistencyCheck(unassignedVal, x, assignment)) {
				List<LocalDate> solution = recursiveBacktracking(assignment, vars);
				if (solution != null) {
					return solution;
				}
			}
			assignment.set(unassignedVal.index, null);
		}
		return null;
	}

	private static int unassignedVal(List<LocalDate> vars) {
		for (int i = 0; i < vars.size(); i++) {
			if (vars.get(i) == null) {
				return i;
			}
		}
		return vars.size();
	}

	private static boolean constraintCheck(String operand, LocalDate leftDate, LocalDate rightDate) {

		switch (operand) {
		case "==":
			if (leftDate.isEqual(rightDate))
				return true;
			break;
		case "!=":
			if (!leftDate.isEqual(rightDate))
				return true;
			break;
		case ">":
			if (leftDate.isAfter(rightDate))
				return true;
			break;
		case "<":
			if (leftDate.isBefore(rightDate))
				return true;
			break;
		case ">=":
			if (leftDate.isAfter(rightDate) || leftDate.isEqual(rightDate))
				return true;
			break;
		case "<=":
			if (leftDate.isBefore(rightDate) || leftDate.isEqual(rightDate))
				return true;
			break;
		}
		return false;
	}

	private static boolean binaryConstraintCheck(DateVar x, List<LocalDate> assignment, LocalDate date,
			BinaryDateConstraint constraint) {

		if (x.index == constraint.L_VAL) {
			if (assignment.get(constraint.R_VAL) == null) {
				return true;
			}
			return constraintCheck(constraint.OP, date, assignment.get(constraint.R_VAL));
		} else {
			if (assignment.get(constraint.L_VAL) == null) {
				return true;
			}
			return constraintCheck(constraint.OP, assignment.get(constraint.L_VAL), date);
		}
	}

	private static boolean consistencyCheck(DateVar x, LocalDate date, List<LocalDate> assignment) {

		for (UnaryDateConstraint unary : x.unaryConstraint) {
			if (!constraintCheck(unary.OP, date, unary.R_VAL)) {
				return false;
			}
		}

		for (BinaryDateConstraint binary : x.binaryConstraint) {
			if (!binaryConstraintCheck(x, assignment, date, binary)) {
				return false;
			}
		}
		return true;
	}

	private static boolean isComplete(List<LocalDate> assignment) {
		for (int i = 0; i < assignment.size(); i++) {
			if (assignment.get(i) == null) {
				return false;
			}
		}
		return true;
	}

	private static List<LocalDate> domainCopy(DateVar var) {
		List<LocalDate> domainCopy = new ArrayList<LocalDate>();
		for (LocalDate copy : var.domain) {
			domainCopy.add(copy);
		}
		return domainCopy;
	}

	private static List<LocalDate> nodeConsistency(DateVar node) {
		List<LocalDate> domainCopy = domainCopy(node);

		for (UnaryDateConstraint u : node.unaryConstraint) {
			if (node.index != u.L_VAL) {
				continue;
			}
			switch (u.OP) {
			case "==":
				for (LocalDate ld : node.domain) {
					if (!ld.equals(u.R_VAL)) {
						domainCopy.remove(ld);
					}
				}
				break;
			case ">":
				for (LocalDate ld : node.domain) {
					if (!ld.isAfter(u.R_VAL)) {
						domainCopy.remove(ld);
					}
				}
				break;
			case "<":
				for (LocalDate ld : node.domain) {
					if (!ld.isBefore(u.R_VAL)) {
						domainCopy.remove(ld);
					}
				}
				break;
			case ">=":
				for (LocalDate ld : node.domain) {
					if (ld.isBefore(u.R_VAL)) {
						domainCopy.remove(ld);
					}
				}
				break;
			case "<=":
				for (LocalDate ld : node.domain) {
					if (ld.isAfter(u.R_VAL)) {
						domainCopy.remove(ld);
					}
				}
				break;
			case "!=":
				for (LocalDate ld : node.domain) {
					if (ld.equals(u.R_VAL)) {
						domainCopy.remove(ld);
					}
				}
				break;

			}
		}
		return domainCopy;
	}

	// [!] TODO: Implement Arc Consistency

	private static class DateVar {

		List<UnaryDateConstraint> unaryConstraint;
		List<BinaryDateConstraint> binaryConstraint;
		int index;
		List<LocalDate> domain;

		DateVar(int index) {
			this.index = index;
			this.domain = new ArrayList<LocalDate>();
			this.unaryConstraint = new ArrayList<UnaryDateConstraint>();
			this.binaryConstraint = new ArrayList<BinaryDateConstraint>();
		}
	}
}
