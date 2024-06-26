package com.hank.snake

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hank.snake.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val viewModel = ViewModelProvider(this).get(SnakeViewModel::class.java)

        viewModel.body.observe(this, Observer {
            binding.contentView.gameView.snakeBody = it
            binding.contentView.gameView.invalidate()
        })

        viewModel.score.observe(this, Observer {
            binding.contentView.score.setText(it.toString())
        })

        viewModel.gameState.observe(this, { gameState ->
            if (gameState == GameState.GAME_OVER) {
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("Game")
                    .setMessage("Game Over")
                    .setPositiveButton("OK", null)
                    .show()
            }
        })

        viewModel.apple.observe(this, Observer {
            binding.contentView.gameView.apple=it
            binding.contentView.gameView.invalidate()
        })

        viewModel.start()

        binding.contentView.top.setOnClickListener { viewModel.move(Direction.TOP) }
        binding.contentView.down.setOnClickListener { viewModel.move(Direction.DOWN) }
        binding.contentView.left.setOnClickListener { viewModel.move(Direction.LEFT) }
        binding.contentView.right.setOnClickListener { viewModel.move(Direction.RIGHT) }

        binding.fab.setOnClickListener { view ->

            viewModel.reset()
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }


}