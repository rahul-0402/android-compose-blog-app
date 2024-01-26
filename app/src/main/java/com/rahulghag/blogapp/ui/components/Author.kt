package com.rahulghag.blogapp.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.rahulghag.blogapp.domain.models.Author

@Composable
fun Author(
    author: Author,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        author.username?.let {
            Text(
                text = it,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        }
    }
}