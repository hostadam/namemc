# NameMCLikes
Do you want to reward players for liking your server on NameMC?
This plugin/API hybrid helps you with that.

# Config
```
The IP to your server (this has to be same as NameMC server)
server-ip: "example.com"

How often should the plugin check for namemc likes? (in ticks, default = 10 minutes)
check-interval: 12000

What rewards should be given to a newly liked user? (commands, use %player% for the player's name)
rewards: []

Don't touch, stores the data for the liked users.
liked-users: []
```

# Setting the plugin up
1. Install the plugin from SpigotMC.
2. Place in your plugins folder
3. Setup the config as you wish.
4. Enjoy!

# For developers
Do you want to, let's say, make your own rewards or your own events when someone likes?
It's simple. Add this plugin as a dependency.

To check if a player has liked, use ```NameMCPlugin.getInstance().hasLiked(UUID uniqueId)```

To do your own code when someone likes, use the ```PlayerLikeEvent```.


