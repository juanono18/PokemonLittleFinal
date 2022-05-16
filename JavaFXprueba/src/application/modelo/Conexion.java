package application.modelo;
	import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import application.DataHandler;


	/**
	 * @author Juan Álvarez Manzano
	 *
	 */
	public class Conexion {

		static String nombreDB = "sql4488347";	//nombre base de datos
		static String usuario = "sql4488347";	//usuario para conexion
		static String pass = "5grhhzTsRJ";			//contraseña para dicha conexion
		static String url = "jdbc:mysql://sql4.freesqldatabase.com:3306/"+nombreDB;	//url de la conexion

		DataHandler dh = new DataHandler();
		static Connection conexion = null; //variable de cpnexion a la BBDD
		
		public Conexion() {
			
		}
		public void conectar() {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");	//cargar clase driver
				try {
					//Damos valores a la variable connection realizando la conexión a la base de datos 
					conexion = DriverManager.getConnection(url,usuario,pass); 
					/**
					 * Si la conexión es correcta es distinto del null por tanto hacemos print para comprobar que es correcto 
					 */
					if(conexion != null) {
						System.out.println("Conexion Establecida");
					/**
					 * Si la conexión no es correcta por tanto es null haremos un print de conexión fallida 
					 */
					}else {
						System.out.println("Conexion Fallida");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		public void createPlayer(Player player) {
			String consulta = "INSERT INTO players (NAM,GENDER,LVL,EXP) VALUES (?,?,?,?)"; //Consulta a realizar 
			PreparedStatement statement = null; //Preparar la query

			try {
				statement = Conexion.getConexion().prepareStatement(consulta); // preparo la consulta 
				statement.setString(1,player.getName());
				statement.setString(2,player.getGender());
				statement.setInt(3,player.getLvl());
				statement.setInt(4,player.getExp());
				
				statement.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.print("ERROR AL HACER EL INSERT PLAYER");
			}
		}
			
		
		public void createCuenta(Cuenta cuenta) {
			String consulta = "INSERT INTO cuenta (username) VALUES (?)"; //Consulta a realizar 
			PreparedStatement statement = null; //Preparar la query

			try {
				statement = Conexion.getConexion().prepareStatement(consulta); // preparo la consulta 
				statement.setString(1,cuenta.getUsername());

				
				statement.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.print("ERROR AL HACER EL INSERT CUENTA");
			}
			
		}
		public void createPokemon(String pkname,Pokemon pokemon, ArrayList<String> pokAtkks) {
			String consulta = "INSERT INTO pokemon (Pkname,alias,vida,defensa,atk,exp,lvl,velocidad,attk1,attk2,attk3,attk4) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)"; //Consulta a realizar 
			PreparedStatement statement = null; //Preparar la query

			try {
				statement = Conexion.getConexion().prepareStatement(consulta); // preparo la consulta 
				statement.setString(1,pkname);
				statement.setString(2,pokemon.getAlias());
				statement.setInt(3,pokemon.getVida());
				statement.setInt(4,pokemon.getDefensa());
				statement.setInt(5,pokemon.getAtaque());
				statement.setInt(6,pokemon.getExperiencia());
				statement.setInt(7,pokemon.getNivel());
				statement.setInt(8,pokemon.getVelocidad());
				statement.setString(9,pokAtkks.get(0));
				statement.setString(10,pokAtkks.get(1));
				statement.setString(11,pokAtkks.get(2));
				statement.setString(12,pokAtkks.get(3));
				statement.execute();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.print("ERROR AL HACER EL INSERT CUENTA");
			}
		}
		public void asignarPokemon() {
			String Pcod = dh.getPlayerCod();
			int pokCod =1;
			String consulta = "SELECT max(codPok) as codPok FROM asignacion order by codPok desc";
			PreparedStatement statement;
			try {
				statement = getConexion().prepareStatement(consulta);
				ResultSet result = null; //Recibir resultado de la query
				result = statement.executeQuery(); //Ejecuto la query y recibo los resultados 
				while(result.next()) {
					if(result.getInt("codPok")!=0) {
						pokCod = result.getInt("codPok")+1;
						
					}
				}
				consulta = "INSERT INTO asignacion (codP,codPok) VALUES (?,?)"; //Consulta a realizar 
				statement = getConexion().prepareStatement(consulta); //Preparo la consulta 
				statement.setInt(1,Integer.parseInt(Pcod));
				statement.setInt(2,pokCod);
				statement.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} //Preparo la consulta 
			
			
		}
		public ArrayList<Cuenta> getCuentas() {
			ArrayList<Cuenta> cuentas = new ArrayList<Cuenta>();
			//descarga de personas
			String consulta = "SELECT * FROM cuenta order by cod";
			PreparedStatement statement = null; //Preparar la query
			ResultSet result = null; //Recibir resultado de la query
			try {
				statement = getConexion().prepareStatement(consulta); //Preparo la consulta 
				result = statement.executeQuery(); //Ejecuto la query y recibo los resultados 
				while(result.next()) {
					Cuenta savep = new Cuenta(
							result.getString("username"));
					cuentas.add(savep);
	
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.print("ERROR AL DESCARGAR LOS DATOS DE CUENTAS");
			}
			return cuentas;
		}
		
		public void asignarCuentas() {
			 String user = getCuentas().get(getCuentas().size()-1).getUsername();
			 int cod =0;
			 String consulta = "SELECT cod FROM cuenta where username='"+user+"'";
				PreparedStatement statement = null; //Preparar la query
				ResultSet result = null; //Recibir resultado de la query
				try {
					statement = getConexion().prepareStatement(consulta); //Preparo la consulta 
					result = statement.executeQuery(); //Ejecuto la query y recibo los resultados 
					while(result.next()) {
						cod = result.getInt("cod");
						System.out.println(cod);
					}
					consulta = "INSERT INTO asignacionC (codC,codP) VALUES (?,?)"; //Consulta a realizar 
					statement = getConexion().prepareStatement(consulta); //Preparo la consulta 
					statement.setInt(1,cod);
					statement.setInt(2,cod);
					statement.execute();
				}
				
				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.print("ERROR AL ACTUALIZAR LOS DATOS CON LA BASE DE DATOS");
				}
			 
		}
		public Player getPlayerData(int cod) {
			Player ply = new Player("","",0,0);
			//descarga de personas
			String consulta = "SELECT * FROM players where cod="+cod;
			PreparedStatement statement = null; //Preparar la query
			ResultSet result = null; //Recibir resultado de la query
			try {
				statement = getConexion().prepareStatement(consulta); //Preparo la consulta 
				result = statement.executeQuery(); //Ejecuto la query y recibo los resultados 
				while(result.next()) {
					ply = new Player(
							result.getString("nam"),
							result.getString("gender"),
							result.getInt("lvl"),
							result.getInt("exp"));
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.print("ERROR AL DESCARGAR LOS DATOS DE CUENTAS");
			}
			return ply;
		
		}
		
		public String getData(String element,String table,String whereClause ) throws SQLException {
			String cod="";
			 String consulta = "SELECT "+element+ " FROM "+table+" "+whereClause;
				PreparedStatement statement = null; //Preparar la query
				ResultSet result = null; //Recibir resultado de la query
				statement = getConexion().prepareStatement(consulta); //Preparo la consulta 
				result = statement.executeQuery(); //Ejecuto la query y recibo los resultados 
				while(result.next()) {
					cod = result.getString(element);
				}
			return cod;
		}
		public ArrayList<String> getMultipleData(String element,String table,String whereClause ) throws SQLException {
			ArrayList<String> cod = new ArrayList<String>();
			 String consulta = "SELECT "+element+ " FROM "+table+" "+whereClause;
				PreparedStatement statement = null; //Preparar la query
				ResultSet result = null; //Recibir resultado de la query
				statement = getConexion().prepareStatement(consulta); //Preparo la consulta 
				result = statement.executeQuery(); //Ejecuto la query y recibo los resultados 
				while(result.next()) {
					cod.add(result.getString(element));
				}
			return cod;
		}
		public void updateData(String table,String changes,String whereClause) {
			String consulta = "Update "+table+" set "+changes+" "+whereClause;
			PreparedStatement statement = null; //Preparar la query
			ResultSet result = null; //Recibir resultado de la query
			try {
				statement = getConexion().prepareStatement(consulta); //Preparo la consulta 
				statement.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void crearPartida(String pokemons, String playerID) {
			String consulta = "INSERT INTO partida (codP1,codP2,P1Poks,P2Poks) VALUES (?,?,?,?)"; //Consulta a realizar 
			PreparedStatement statement = null; //Preparar la query

			try {
				statement = Conexion.getConexion().prepareStatement(consulta); // preparo la consulta 
				statement.setInt(1,Integer.parseInt(playerID));
				statement.setInt(2,0);
				statement.setString(3,pokemons);
				statement.setString(4,"nan");
				statement.execute();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.print("ERROR AL HACER EL INSERT PARTIDA");
			}
		}
				
		public void mergeMatch(String playerID, String P1poks) {
			// TODO Auto-generated method stub
			int P2=0;
			String P2Pok="";
			try {
				P2 = Integer.parseInt(getData("codP1", "partida", "order by cod desc"));
				P2Pok=getData("P1Poks", "partida", "where codP1="+P2);
				
			} catch (NumberFormatException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String consulta = "truncate partida"; //Consulta a realizar 
			PreparedStatement statement = null; //Preparar la query
			try {
				statement = Conexion.getConexion().prepareStatement(consulta);
				statement.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			consulta = "INSERT INTO partida (codP1,codP2,P1Poks,P2Poks) VALUES (?,?,?,?)"; //Consulta a realizar 
			statement = null; //Preparar la query

			try {
				statement = Conexion.getConexion().prepareStatement(consulta); // preparo la consulta 
				statement.setInt(1,Integer.parseInt(playerID));
				statement.setInt(2,P2);
				statement.setString(3,P1poks);
				statement.setString(4,P2Pok);
				statement.execute();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.print("ERROR AL HACER EL INSERT PARTIDA");
			}
			
		}
		public void setTurn(String turn) {
			String consulta = "INSERT INTO acciones (turn) VALUES (?)"; //Consulta a realizar 
			PreparedStatement statement = null; //Preparar la query

			try {
				statement = Conexion.getConexion().prepareStatement(consulta); // preparo la consulta 
				statement.setString(1,turn);
				statement.execute();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.print("ERROR AL HACER EL INSERT ACCION");
			}
		}
		public void setTurnAcction(String turn,String accion,int dmg,String currSenPok) {
			String consulta = "INSERT INTO acciones (turn,accion,dmg,currSenPok) VALUES (?,?,?,?)"; //Consulta a realizar 
			PreparedStatement statement = null; //Preparar la query

			try {
				statement = Conexion.getConexion().prepareStatement(consulta); // preparo la consulta 
				statement.setString(1,turn);
				statement.setString(2,accion);
				statement.setInt(3,dmg);
				statement.setString(4,currSenPok);
				statement.execute();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.print("ERROR AL HACER EL INSERT ACCION");
			}
		}
	
			
		

		
		public static Connection getConexion() {
			return conexion;
			
		}
		public void desconectar() {
			conexion = null;
			
		}
		/**
		 * @param conexion the conexion to set
		 */
		public static void setConexion(Connection conexion) {
			Conexion.conexion = conexion;
		}
		

}

