package music_trainer.webapp

import angulate2.std._
import angulate2.platformBrowser.BrowserModule
import org.scalajs.dom.document
import music_trainer.scale.Instrument
import music_trainer.scale.Player

import music_trainer.webapp.component._

@NgModule(
  imports = @@[BrowserModule],
  declarations = @@[AppComponent,ExerciseList,ExerciseItem,ExerciseView,AnswersList,AnswerBox],
  bootstrap = @@[AppComponent]
)
class AppModule {

}

@Component(
  selector = "my-app",
  templateUrl = "resources/app.html"
)
class AppComponent extends OnInit {

  @ViewChild("ev")
  var exerciseView: ExerciseView = _

  var num = -1

  def returnFromExercise(code: Int){
    num = -1
    exerciseView.stopExercise()
  }

  def menuSel(item: SelectedOption){
    num = item.id
    exerciseView.changeExercise(num, item.numOfExercises, item.instrument)
  }

  override def ngOnInit(): Unit = { new Player(new Instrument("Piano"), 127, () => document.body.classList.remove("loading")); }

}
