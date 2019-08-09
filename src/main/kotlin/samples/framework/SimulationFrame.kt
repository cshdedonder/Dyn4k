package samples.framework

import org.dyn4j.dynamics.World
import java.awt.*
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.awt.geom.AffineTransform
import javax.swing.JFrame
import javax.swing.UIManager

abstract class SimulationFrame(name: String, protected val scale: Double) : JFrame(name) {

    protected val world = World()
    protected val canvas = Canvas()

    private var stopped = false
    private var paused = false
    private var last = 0L

    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        this.addWindowListener(object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent?) {
                stop()
                super.windowClosing(e)
            }
        })
        val size = Dimension(800, 600)
        with(canvas) {
            preferredSize = size
            minimumSize = size
            maximumSize = size
            add(this)
        }
        isResizable = false
        this.pack()
        this.initializeWorld()
    }

    protected abstract fun initializeWorld()

    private fun start() {
        last = System.nanoTime()
        canvas.ignoreRepaint = true
        canvas.createBufferStrategy(2)
        val thread = object : Thread() {
            override fun run() {
                while (!stopped) {
                    gameLoop()
                    Thread.sleep(5L)
                }
            }
        }
        thread.isDaemon = true
        thread.start()
    }

    private fun gameLoop() {
        val g = canvas.bufferStrategy.drawGraphics as Graphics2D
        transform(g)
        clear(g)
        val time = System.nanoTime()
        val diff = time - last
        last = time
        val elapsedTime = diff.toDouble() / NANO_TO_BASE
        render(g, elapsedTime)
        if (!paused) {
            update(g, elapsedTime)
        }
        g.dispose()
        with(canvas.bufferStrategy) {
            if (!contentsLost()) {
                show()
            }
        }
        Toolkit.getDefaultToolkit().sync()
    }

    protected fun transform(g: Graphics2D) {
        val w = canvas.width.toDouble()
        val h = canvas.height.toDouble()

        val yFlip = AffineTransform.getScaleInstance(1.0, -1.0)
        val move = AffineTransform.getTranslateInstance(w / 2, -h / 2)
        with(g) {
            transform(yFlip)
            transform(move)
        }
    }

    protected fun clear(g: Graphics2D) {
        val w = canvas.width
        val h = canvas.height

        with(g) {
            color = Color.WHITE
            fillRect(-w / 2, -h / 2, w, h)
        }
    }

    protected open fun render(g: Graphics2D, elapsedTime: Double) {
        with(g) {
            setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE)
            setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        }
        for (body in world.bodies) {
            render(g, elapsedTime, body as SimulationBody)
        }
    }

    protected fun render(g: Graphics2D, elapsedTime: Double, body: SimulationBody) {
        body.render(g, scale)
    }

    protected fun update(g: Graphics2D, elapsedTime: Double) {
        world.update(elapsedTime)
    }

    fun stop() {
        stopped = true
    }

    fun isStopped(): Boolean = stopped

    fun pause() {
        paused = true
    }

    fun resume() {
        paused = false
    }

    fun isPaused(): Boolean = paused

    fun run() {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
        isVisible = true
        start()
    }

    companion object {
        const val NANO_TO_BASE = 1.0E9
    }
}