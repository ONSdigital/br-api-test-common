package uk.gov.ons.br.test.json

object JsonString {
  val EmptyValues = Seq.empty[Option[String]]

  def withString(named: String, withValue: String): Option[String] =
    Some(s""""$named":"$withValue"""")

  def withOptionalString(named: String, withValue: Option[String]): Option[String] =
    withValue.flatMap(withString(named, _))

  def withInt(named: String, withValue: Int): Option[String] =
    Some(s""""$named":$withValue""")

  def withOptionalInt(named: String, withValue: Option[Int]): Option[String] =
    withValue.flatMap(withInt(named, _))

  // for top-level object
  def ofObject(values: Option[String]*): String =
    values.flatten.mkString("{", ",", "}")

  // for sub-object
  def withObject(named: String, values: Option[String]*): Option[String] =
    Some(s""""$named":${ofObject(values: _*)}""")

  def withOptionalObject(named: String, values: Option[String]*): Option[String] =
    if (values.exists(_.isDefined)) withObject(named, values: _*)
    else None
}
