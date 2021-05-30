<?php
/**
 * The base configuration for WordPress
 *
 * The wp-config.php creation script uses this file during the
 * installation. You don't have to use the web site, you can
 * copy this file to "wp-config.php" and fill in the values.
 *
 * This file contains the following configurations:
 *
 * * MySQL settings
 * * Secret keys
 * * Database table prefix
 * * ABSPATH
 *
 * @link https://wordpress.org/support/article/editing-wp-config-php/
 *
 * @package WordPress
 */

// ** MySQL settings - You can get this info from your web host ** //
/** The name of the database for WordPress */
define( 'DB_NAME', 'athleticus' );

/** MySQL database username */
define( 'DB_USER', 'root' );

/** MySQL database password */
define( 'DB_PASSWORD', '' );

/** MySQL hostname */
define( 'DB_HOST', 'localhost' );

/** Database Charset to use in creating database tables. */
define( 'DB_CHARSET', 'utf8mb4' );

/** The Database Collate type. Don't change this if in doubt. */
define( 'DB_COLLATE', '' );

/**#@+
 * Authentication Unique Keys and Salts.
 *
 * Change these to different unique phrases!
 * You can generate these using the {@link https://api.wordpress.org/secret-key/1.1/salt/ WordPress.org secret-key service}
 * You can change these at any point in time to invalidate all existing cookies. This will force all users to have to log in again.
 *
 * @since 2.6.0
 */
define( 'AUTH_KEY',         'O]eB5yH8$qFa[+cQd5kea0Q=^Q!}9RNF3]y@a<I*wA|1%Khzk!+SH2inkbeXGm1l' );
define( 'SECURE_AUTH_KEY',  'Yxnyo`0vsuRFU`:2XO3^7H]b5W;VW+@x6.b x*f>}5C9~ksUl_Mqao>AAv-wYEXy' );
define( 'LOGGED_IN_KEY',    'UCt-xt37k$&&&iQ5]=j~9W|x+iY|z#[^!I_Mj%.FV2?(~dFm7,sy%TUZ}!7l-mTM' );
define( 'NONCE_KEY',        'P_5_TuaiO_v&KKjuX2S(eqaTjW)+GvM{}[qu,jeZ|Q;XyiEIpc,/ER^NI~H:.L#m' );
define( 'AUTH_SALT',        'z0X!dmC?I4.5eo=Qm314?B}*vE@tlwR=3}O6Z;`U[%UZS*hGYqm({AqD#1,5~G1z' );
define( 'SECURE_AUTH_SALT', '9u`C2r-nKMZ4)k6W,G_sEx}PI7th`z]dd&J8?T7uDB_Z$y5o%uy0eh%~+`b*hh0q' );
define( 'LOGGED_IN_SALT',   'O9CDZ,UQn#j2JK5hy#_6&BG?j|7i9pMFqI.w[*-oo)iA:S 1KYpk>D?jU,d#df7j' );
define( 'NONCE_SALT',       'R1zud)pL8vWJef2!*DF:_Mg^w9.9jg1A6`mJV5yG*E`1}e%G_pfZpKd^ME9X[>}l' );

/**#@-*/

/**
 * WordPress Database Table prefix.
 *
 * You can have multiple installations in one database if you give each
 * a unique prefix. Only numbers, letters, and underscores please!
 */
$table_prefix = 'wp_';

/**
 * For developers: WordPress debugging mode.
 *
 * Change this to true to enable the display of notices during development.
 * It is strongly recommended that plugin and theme developers use WP_DEBUG
 * in their development environments.
 *
 * For information on other constants that can be used for debugging,
 * visit the documentation.
 *
 * @link https://wordpress.org/support/article/debugging-in-wordpress/
 */
define( 'WP_DEBUG', false );

/* That's all, stop editing! Happy publishing. */

/** Absolute path to the WordPress directory. */
if ( ! defined( 'ABSPATH' ) ) {
	define( 'ABSPATH', __DIR__ . '/' );
}

/** Sets up WordPress vars and included files. */
require_once ABSPATH . 'wp-settings.php';
