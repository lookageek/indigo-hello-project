import indigo.*
import indigo.shared.config.GameViewport
import indigo.shared.datatypes.RGBA

import scala.scalajs.js.annotation.JSExportTopLevel

@JSExportTopLevel("IndigoGame") // Pandering to mdoc
object HelloIndigo extends IndigoSandbox[Unit, Model] {

  val config: GameConfig =
    GameConfig.default.withViewport(GameViewport(720, 480))

  val animations: Set[Animation] =
    Set()

  val assetName = AssetName("dots")

  val assets: Set[AssetType] =
    Set(AssetType.Image(assetName, AssetPath("assets/dots.png")))

  val fonts: Set[FontInfo] =
    Set()

  val shaders: Set[Shader] =
    Set()

  def setup(
      assetCollection: AssetCollection,
      dice: Dice
  ): Outcome[Startup[Unit]] =
    Outcome(Startup.Success(()))

  def initialModel(startupData: Unit): Outcome[Model] =
    Outcome(Model.initial(config.viewport.center))

  def updateModel(
      context: FrameContext[Unit],
      model: Model
  ): GlobalEvent => Outcome[Model] = {
    case e: MouseEvent.Click =>
      val clickPoint = e.position
      val adjustedPosition = clickPoint - model.center

      Outcome(
        model.addDot(
          Dot(
            Point.distanceBetween(model.center, clickPoint).toInt,
            Radians(
              Math.atan2(
                adjustedPosition.x.toDouble,
                adjustedPosition.y.toDouble
              )
            )
          )
        )
      )

    case FrameTick => Outcome(model.update(context.delta))
    case _ => Outcome(model)
  }

  def present(
      context: FrameContext[Unit],
      model: Model
  ): Outcome[SceneUpdateFragment] =
    Outcome(
      SceneUpdateFragment(
        Graphic(Rectangle(0, 0, 32, 32), 1, Material.Bitmap(assetName)) :: drawDots(model.center, model.dots))
    )

  def drawDots(center: Point, dots: Batch[Dot]): Batch[Graphic[_]] =
    dots.map { dot =>
      val position = Point(
        (Math.sin(dot.angle.toDouble) * dot.orbitDistance + center.x).toInt,
        (Math.cos(dot.angle.toDouble) * dot.orbitDistance + center.y).toInt
      )

      Graphic(
        Rectangle(0, 0, 32, 32), 1, Material.Bitmap(assetName)
      ).withCrop(Rectangle(0, 0, 16, 16)).moveTo(position)
    }

}
