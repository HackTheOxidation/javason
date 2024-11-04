{
  stdenv,
  jdk21_headless,
  maven,
  makeWrapper
}:
let
  pname = "javason";
  version = "0.1.1";
  name = "${pname}-${version}";
in 
stdenv.mkDerivation {
  inherit pname version name;
  src = ./.;
  buildInputs = [ jdk21_headless maven makeWrapper ];
  buildPhase = ''
    mvn clean antlr4:antlr4 package
  '';

  installPhase = ''
    mkdir -p $out/bin

    cp target/${name}.jar $out/

    makeWrapper ${jdk21_headless}/bin/java $out/bin/${pname} --add-flags "-jar $out/${name}.jar"
  '';

}
