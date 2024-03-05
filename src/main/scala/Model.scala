final case class Model(counter: Int) {
    def update: Model = copy(counter = counter + 1)
}

object Model {
    def initial: Model = Model(counter = 0)
}
