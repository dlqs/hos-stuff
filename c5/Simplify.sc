sealed trait Expr
case class BinOp(left: Expr, op: String, right: Expr) extends Expr
case class Literal(value: Int) extends Expr
case class Variable(name: String) extends Expr

def stringify(expr: Expr): String = expr match {
    case BinOp(left, op, right) => s"(${stringify(left)} $op ${stringify(right)})"
    case Literal(value) => value.toString
    case Variable(name) => name
  }

def simplify(exp: Expr): Expr = {
      val res = exp match {
      case BinOp(Literal(l), "+", Literal(r)) => Literal(l + r)
      case BinOp(Literal(l), "-", Literal(r)) => Literal(l - r)
      case BinOp(Literal(l), "*", Literal(r)) => Literal(l * r)
      
      case BinOp(Literal(1), "*", r) => simplify(r)
      case BinOp(l, "*", Literal(1)) => simplify(l)
      case BinOp(Literal(0), "*", r) => Literal(0)
      case BinOp(l, "*", Literal(0)) => Literal(0)
      case BinOp(Literal(0), "+", r) => simplify(r)
      case BinOp(l, "+", Literal(0)) => simplify(l)
      case BinOp(l, "-", Literal(0)) => simplify(l)
      
      case BinOp(left, op, right) => BinOp(simplify(left), op, simplify(right))
      
      case Literal(value) => Literal(value)
      case Variable(name) => Variable(name)
  }
  if (res == exp) {
    return res
  } else {
    return simplify(res)
  }
}