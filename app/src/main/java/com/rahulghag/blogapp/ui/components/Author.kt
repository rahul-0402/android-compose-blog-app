package com.rahulghag.blogapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rahulghag.blogapp.R
import com.rahulghag.blogapp.domain.models.Author

@Composable
fun Author(
    author: Author,
    modifier: Modifier = Modifier,
    showFollowButton: Boolean = false,
    onFollowButtonClick: ((Boolean, String) -> Unit)? = null
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        author.username?.let {
            Text(
                text = it,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            if (showFollowButton) {
                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = "\u2022",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = if (author.isFollowing) {
                        stringResource(R.string.following)
                    } else {
                        stringResource(R.string.follow)
                    },
                    modifier = Modifier
                        .clickable {
                            onFollowButtonClick?.invoke(author.isFollowing, author.username)
                        },
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}