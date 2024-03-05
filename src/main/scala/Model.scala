import indigo.shared.datatypes.Point
import indigo.{Radians, Seconds}
import indigo.shared.collections.Batch
final case class Model(center: Point, dots: Batch[Dot]) {
    def addDot(dot: Dot): Model = copy(dots = dot :: dots)
    def update(timeDelta: Seconds): Model = copy(dots = dots.map(_.update(timeDelta)))
}

object Model {
    def initial(center: Point): Model = Model(center, Batch.empty)
}

final case class Dot(orbitDistance: Int, angle: Radians) {
    def update(timeDelta: Seconds): Dot =
        this.copy(angle = angle + Radians.fromSeconds(timeDelta))
}
